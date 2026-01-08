package com.xasdify.pocketflow.core.di

import com.xasdify.pocketflow.data.local.DatabaseFactory
import com.xasdify.pocketflow.data.storage.KeyValueStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory(androidApplication()) }
        single { KeyValueStore(get()) }
    }