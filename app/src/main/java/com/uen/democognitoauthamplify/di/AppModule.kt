package com.uen.democognitoauthamplify.di

import android.app.Application
import com.uen.democognitoauthamplify.network.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAmplifyManager(application: Application): NetworkMonitor {
        return  NetworkMonitor(application)
    }
}
