package com.app.floo.domain.repository

import com.app.floo.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun connectToMQTT(): Flow<Resource<Nothing>>

    fun disconnectFromMQTT(): Flow<Resource<Nothing>>

    fun isMQTTConnected(): Boolean

    fun subscribeToTopic(topic: String, qosLevel: Int?): Flow<Resource<Nothing>>

    fun unSubscribeFromTopic(topic: String): Flow<Resource<Nothing>>

    fun retrieveMessageFromPublisher(): Flow<Resource<String>>

}