package com.app.floo.di

import com.app.floo.domain.usecase.MqttInteractor
import com.app.floo.domain.usecase.MqttUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMqttUseCase(mqttInteractor: MqttInteractor): MqttUseCase

}