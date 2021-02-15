package com.app.floo.data.remote.vo

import org.eclipse.paho.client.mqttv3.MqttException

sealed class MqttResponse<out R> {
    data class Success(val message: String) : MqttResponse<Nothing>()
    data class Failure(val exception: Throwable?, val message: String?) : MqttResponse<Nothing>()
    data class Error(val exception: MqttException, val message: String?) : MqttResponse<Nothing>()
    data class UnknownError(val exception: Throwable, val message: String?) : MqttResponse<Nothing>()
    data class ConnectionLost(val exception: Throwable?) : MqttResponse<Nothing>()
    data class MessageReceived<out T>(val data: T, val topic: String) : MqttResponse<T>()
}
