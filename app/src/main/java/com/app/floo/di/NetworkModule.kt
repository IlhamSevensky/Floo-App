package com.app.floo.di

import android.content.Context
import com.app.floo.BuildConfig
import com.app.floo.data.remote.mqtt.MQTTClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient
import javax.inject.Named
import javax.inject.Singleton

/**
 * ApplicationComponent being renamed to SingletonComponent refer to dagger 2.28.2
 */
@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val MQTT_SERVER_URI = "MQTT_SERVER_URI"
    private const val MQTT_SERVER_TEST_URI = "MQTT_SERVER_TEST_URI"

    @Provides
    @Named(MQTT_SERVER_URI)
    fun provideMQTTServerUri(): String = BuildConfig.MQTT_SERVER_URI_KEY

    @Provides
    @Named(MQTT_SERVER_TEST_URI)
    fun provideMQTTServerTestUri(): String = BuildConfig.MQTT_SERVER_URI_TEST_KEY

    @Singleton
    @Provides
    fun provideMQTTAndroidClient(
        @ApplicationContext appContext: Context,
        @Named(MQTT_SERVER_URI) serverUri: String
    ): MqttAndroidClient {
        val clientId = MqttClient.generateClientId()
        return MqttAndroidClient(appContext, serverUri, clientId)
    }

    @Provides
    fun provideMQTTClient(mqttAndroidClient: MqttAndroidClient): MQTTClient = MQTTClient(mqttAndroidClient)

}