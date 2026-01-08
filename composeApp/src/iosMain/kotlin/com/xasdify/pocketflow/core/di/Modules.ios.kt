package com.xasdify.pocketflow.core.di


import com.xasdify.pocketflow.core.data.database.DatabaseFactory
import com.xasdify.pocketflow.data.storage.KeyValueStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single { KeyValueStore() }
    }