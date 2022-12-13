package com.infnet.devandroidat.application

import android.app.Application
import com.infnet.devandroidat.repository.DevAndroidATRepository

class DevAndroidATApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DevAndroidATRepository.initialize()
    }
}