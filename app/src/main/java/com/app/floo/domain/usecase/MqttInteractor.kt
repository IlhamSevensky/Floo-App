package com.app.floo.domain.usecase

import com.app.floo.domain.repository.MainRepository
import com.app.floo.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MqttInteractor @Inject constructor(
    private val mainRepository: MainRepository
) : MqttUseCase {

    override fun connectToMQTT(): Flow<Resource<Nothing>> = mainRepository.connectToMQTT()

    override fun disconnectFromMQTT(): Flow<Resource<Nothing>> = mainRepository.disconnectFromMQTT()

    override fun isMQTTConnected(): Boolean = mainRepository.isMQTTConnected()

    override fun subscribeToTopic(topic: String, qosLevel: Int?): Flow<Resource<Nothing>> =
        mainRepository.subscribeToTopic(topic, qosLevel)

    override fun retrieveMessageFromPublisher(): Flow<Resource<String>> =
        mainRepository.retrieveMessageFromPublisher()

    override fun unSubscribeFromTopic(topic: String): Flow<Resource<Nothing>> =
        mainRepository.unSubscribeFromTopic(topic)

}