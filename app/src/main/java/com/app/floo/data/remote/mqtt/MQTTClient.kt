package com.app.floo.data.remote.mqtt

import com.app.floo.data.remote.vo.MqttResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.sql.Timestamp
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MQTTClient @Inject constructor(private val client: MqttAndroidClient) {

    companion object {
        const val QOS_LEVEL_1 = 1
    }

    fun connectToMQTT(): Flow<MqttResponse<Nothing>> = callbackFlow {
        client.connect(null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Timber.d("[SUCCESS] connected to ${client.serverURI}")
                offer(MqttResponse.Success("Successfully connected to ${client.serverURI}"))
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Timber.d("[FAILURE] connected to ${client.serverURI} caused by $exception")
                offer(MqttResponse.Failure(exception, client.serverURI))
            }
        })

        awaitClose { close() }
    }

    fun retrieveMessageFromPublisher(): Flow<MqttResponse<String>> = callbackFlow {
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Timber.d("[LOST CONNECTION] caused by $cause")
                offer(MqttResponse.ConnectionLost(cause))
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                val currentTimestamp = Timestamp(System.currentTimeMillis())
                Timber.d("[MESSAGE RECEIVED] from topic : $topic with message : $message at $currentTimestamp")
                offer(MqttResponse.MessageReceived(message.toString(), topic.toString()))
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Timber.d("[DELIVERY COMPLETE] from ${client.serverURI}")
            }
        })

        awaitClose { close() }

    }

    fun subscribeToTopic(
        topic: String,
        qosLevel: Int?
    ): Flow<MqttResponse<Nothing>> = callbackFlow {
        client.subscribe(
            topic,
            qosLevel ?: QOS_LEVEL_1,
            null,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Timber.d("[SUCCESS] subscribe on topic : $topic")
                    offer(MqttResponse.Success("Successfully subscribe to topic $topic"))
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Timber.d("[FAILURE] subscribe on topic : $topic caused by $exception")
                    offer(MqttResponse.Failure(exception, topic))
                }
            }
        )

        awaitClose { close() }
    }

    fun unSubscribeTopic(topic: String): Flow<MqttResponse<Nothing>> = callbackFlow {
        client.unsubscribe(topic, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Timber.d("[SUCCESS] unsubscribe on topic : $topic")
                offer(MqttResponse.Success(topic))
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Timber.d("[FAILURE] unsubscribe on topic : $topic caused by $exception")
                offer(MqttResponse.Failure(exception, topic))
            }
        })

        awaitClose { close() }
    }

    fun disconnectMQTT(): Flow<MqttResponse<Nothing>> = callbackFlow {
        client.disconnect(null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Timber.d("[SUCCESS] disconnected from ${client.serverURI}")
                offer(MqttResponse.Success("Successfully disconnected from ${client.serverURI}"))
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Timber.d("[FAILURE] disconnected to ${client.serverURI} caused by $exception")
                offer(MqttResponse.Failure(exception, client.serverURI))
            }
        })

        awaitClose { close() }
    }

    fun isMQTTConnected(): Boolean = client.isConnected

}