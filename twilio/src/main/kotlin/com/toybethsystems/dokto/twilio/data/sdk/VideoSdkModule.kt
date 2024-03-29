package com.toybethsystems.dokto.twilio.data.sdk

import android.content.Context
import android.content.SharedPreferences
import com.toybethsystems.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybethsystems.dokto.twilio.data.rest.TwilioAuthDataSource
import com.toybethsystems.dokto.twilio.data.rest.TwilioRestApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class VideoSdkModule {

    @Provides
    @Singleton
    fun providesRoomManager(
        @ApplicationContext application: Context,
        sharedPreferences: TwilioSharedPreference,
        tokenService: TwilioAuthDataSource
    ): RoomManager {
        val connectOptionsFactory =
            ConnectOptionsFactory(sharedPreferences, tokenService)
        val videoClient = VideoClient(application, connectOptionsFactory)
        return RoomManager(application, videoClient)
    }
}
