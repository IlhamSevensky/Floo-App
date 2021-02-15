package com.app.floo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.floo.domain.usecase.MqttUseCase
import com.app.floo.extensions.getEpochTimestamp
import com.app.floo.ui.main.graph.GraphFragment.Companion.WATER_DISTANCE_DATA_SET_INDEX
import com.app.floo.utils.Constants.TOPIC_STATUS
import com.app.floo.utils.Constants.TOPIC_WATER_DISTANCE
import com.app.floo.vo.Resource
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mqttUseCase: MqttUseCase
) : ViewModel() {

    private var connectionMqttJob: Job? = null

    private val _waterDistanceLineData: LineData = LineData()

    private val _stateWaterDistanceUpdateChart = MutableLiveData<Int>()

    private val _stateWaterDistanceTopicMessage = MutableLiveData<String>()

    private val _stateStatusTopicMessage = MutableLiveData<String>()

    private val _stateMqttConnection = MutableLiveData<Resource<Nothing>>()

    private val _stateMqttDisconnect = MutableLiveData<Resource<Nothing>>()

    private val _stateUnSubscribeTopic = MutableLiveData<Resource<Nothing>>()

    private val _stateSubscribeToTopic = MutableLiveData<Resource<Nothing>>()

    private val _stateMessageFromPublisher = MutableLiveData<Resource<String>>()

    fun getWaterDistanceLineData(): LineData = _waterDistanceLineData

    fun getStateStatusTopicMessage(): LiveData<String> = _stateStatusTopicMessage

    fun getStateWaterDistanceTopicMessage(): LiveData<String> = _stateWaterDistanceTopicMessage

    fun getStateWaterDistanceUpdateChart(): LiveData<Int> = _stateWaterDistanceUpdateChart

    fun getStateMqttConnection(): LiveData<Resource<Nothing>> = _stateMqttConnection

    fun getStateMqttDisconnection(): LiveData<Resource<Nothing>> = _stateMqttDisconnect

    fun getStateUnSubscribeTopic(): LiveData<Resource<Nothing>> = _stateUnSubscribeTopic

    fun getStateSubscribeToTopic(): LiveData<Resource<Nothing>> = _stateSubscribeToTopic

    fun getStateMessageFromPublisher(): MutableLiveData<Resource<String>> = _stateMessageFromPublisher

    fun connectToMQTT() {
        connectionMqttJob?.cancel()
        viewModelScope.launch {
            mqttUseCase.connectToMQTT().collect { response ->
                _stateMqttConnection.value = response
            }
        }
    }

    fun reqMessageFromPublisher() {
        viewModelScope.launch {
            mqttUseCase.retrieveMessageFromPublisher().collectLatest { response ->
                when (response){
                    is Resource.MessageReceived -> when (response.topic) {
                        TOPIC_STATUS -> _stateStatusTopicMessage.value = response.data
                        TOPIC_WATER_DISTANCE -> {
                            updateWaterDistanceEntry(response)
                            _stateWaterDistanceUpdateChart.value = _waterDistanceLineData.entryCount
                            _stateWaterDistanceTopicMessage.value = response.data
                        }
                    }

                    else -> _stateMessageFromPublisher.value = response
                }

            }
        }
    }

    fun subscribeToTopic(topic: String, qosLevel: Int?) {
        viewModelScope.launch {
            mqttUseCase.subscribeToTopic(topic, qosLevel).collect { response ->
                _stateSubscribeToTopic.postValue(response)
            }
        }
    }

    fun unSubscribeFromTopic(topic: String) {
        viewModelScope.launch {
            mqttUseCase.unSubscribeFromTopic(topic).collect { response ->
                _stateUnSubscribeTopic.postValue(response)
            }
        }
    }

    fun disconnectFromMQTT() {
        viewModelScope.launch {
            mqttUseCase.disconnectFromMQTT().collect { response ->
                _stateMqttDisconnect.value = response
            }
        }
    }

    fun isMQTTConnected(): Boolean = mqttUseCase.isMQTTConnected()

    private var _referenceEpochTimestamp: Long = 0L
    private var _isFirstDataReceived : Boolean = false
    private var _stateReferenceEpochTimestamp = MutableLiveData<Long>()

    fun getStateReferenceEpochTimestamp(): LiveData<Long> = _stateReferenceEpochTimestamp

    private fun updateWaterDistanceEntry(response: Resource.MessageReceived<String>) {
        val waterDistance = response.data.toFloat()
        val currentEpochTimestamp = getEpochTimestamp()

        if (!_isFirstDataReceived){
            _referenceEpochTimestamp = currentEpochTimestamp
            _stateReferenceEpochTimestamp.value = currentEpochTimestamp
            _isFirstDataReceived = true
        }

        val formattedEpochTimestamp = currentEpochTimestamp - _referenceEpochTimestamp

        val newEntry = Entry(formattedEpochTimestamp.toFloat(), waterDistance)
        _waterDistanceLineData.addEntry(newEntry, WATER_DISTANCE_DATA_SET_INDEX)
    }

}