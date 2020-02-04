package com.mahmoudibra.advice.app

import android.app.Application
import com.mahmoudibra.advice.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

class AdviceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
