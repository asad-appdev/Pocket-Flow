package com.xasdify.pocketflow.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.xasdify.pocketflow.core.data.database.DatabaseFactory
import com.xasdify.pocketflow.core.data.network.HttpClientFactory

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module


val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    //  single { get<AppDatabase>().favoriteBookDao }
}

