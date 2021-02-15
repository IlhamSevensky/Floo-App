package com.app.floo.data

import com.app.floo.data.remote.RemoteDataSource
import com.app.floo.data.remote.vo.MqttResponse
import com.app.floo.domain.repository.MainRepository
import com.app.floo.vo.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MainRepository {

    override fun connectToMQTT(): Flow<Resource<Nothing>> = flow {

        remoteDataSource.connectToMQTT().collect { response ->
            when (response) {
                is MqttResponse.Success -> emit(Resource.Success(response.message))
                is MqttResponse.Failure -> emit(
                    Resource.Failure(
                        response.exception,
                        response.message
                    )
                )
                is MqttResponse.Error -> emit(Resource.Error(response.exception, response.message))
                is MqttResponse.UnknownError -> emit(
                    Resource.UnknownError(
                        response.exception,
                        response.message
                    )
                )
                else -> throw IllegalStateException("Undefined State")
            }
        }

    }.flowOn(IO)

    override fun disconnectFromMQTT(): Flow<Resource<Nothing>> = flow {

        remoteDataSource.disconnectFromMQTT().collect { response ->
            when (response) {
                is MqttResponse.Success -> emit(Resource.Success(response.message))
                is MqttResponse.Failure -> emit(
                    Resource.Failure(
                        response.exception,
                        response.message
                    )
                )
                is MqttResponse.Error -> emit(Resource.Error(response.exception, response.message))
                is MqttResponse.UnknownError -> emit(
                    Resource.UnknownError(
                        response.exception,
                        response.message
                    )
                )
                else -> throw IllegalStateException("Undefined State")
            }
        }

    }.flowOn(IO)

    override fun isMQTTConnected(): Boolean = remoteDataSource.isMQTTConnected()

    override fun subscribeToTopic(
        topic: String,
        qosLevel: Int?
    ): Flow<Resource<Nothing>> = flow {

        remoteDataSource.subscribeToTopic(topic, qosLevel).collect { response ->
            when (response) {
                is MqttResponse.Success -> emit(Resource.Success(response.message))
                is MqttResponse.Failure -> emit(
                    Resource.Failure(
                        response.exception,
                        response.message
                    )
                )
                is MqttResponse.Error -> emit(Resource.Error(response.exception, response.message))
                is MqttResponse.UnknownError -> emit(
                    Resource.UnknownError(
                        response.exception,
                        response.message
                    )
                )
                else -> throw IllegalStateException("Undefined State")
            }
        }

    }.flowOn(IO)

    override fun unSubscribeFromTopic(topic: String): Flow<Resource<Nothing>> = flow {

        remoteDataSource.unSubscribeFromTopic(topic).collect { response ->
            when (response) {
                is MqttResponse.Success -> emit(Resource.Success(response.message))
                is MqttResponse.Failure -> emit(
                    Resource.Failure(
                        response.exception,
                        response.message
                    )
                )
                is MqttResponse.Error -> emit(Resource.Error(response.exception, response.message))
                is MqttResponse.UnknownError -> emit(
                    Resource.UnknownError(
                        response.exception,
                        response.message
                    )
                )
                else -> throw IllegalStateException("Undefined State")
            }
        }

    }.flowOn(IO)

    override fun retrieveMessageFromPublisher(): Flow<Resource<String>> = flow {

        remoteDataSource.retrieveMessageFromPublisher().collect { response ->
            when (response) {
                is MqttResponse.Error -> emit(Resource.Error(response.exception, response.message))
                is MqttResponse.UnknownError -> emit(
                    Resource.UnknownError(
                        response.exception,
                        response.message
                    )
                )
                is MqttResponse.ConnectionLost -> emit(Resource.ConnectionLost(response.exception))
                is MqttResponse.MessageReceived -> emit(
                    Resource.MessageReceived(
                        response.data,
                        response.topic
                    )
                )
                else -> throw IllegalStateException("Undefined State")
            }
        }

    }.flowOn(IO)


}