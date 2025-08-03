package com.xasdify.pocketflow

import android.app.Application
import com.xasdify.pocketflow.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class PocketFlowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@PocketFlowApplication) }
    }
}