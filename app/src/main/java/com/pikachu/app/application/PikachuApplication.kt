package com.pikachu.app.application

import android.app.Application
import com.pikachu.app.sharedPreferences.PreferenceHelper

class PikachuApplication:Application() {
    override fun onCreate() {
        super.onCreate()
//        ApiClient.initialize(this)
//        AnalyticsHelper.initialize(this)
        PreferenceHelper.initialize(this)
        //createNotificationChannels()
    }
}