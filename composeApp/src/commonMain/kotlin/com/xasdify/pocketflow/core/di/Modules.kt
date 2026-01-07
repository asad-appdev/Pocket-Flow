package com.xasdify.pocketflow.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.xasdify.pocketflow.core.data.database.DatabaseFactory
import com.xasdify.pocketflow.core.data.network.HttpClientFactory
import com.xasdify.pocketflow.data.local.AppDatabase
import com.xasdify.pocketflow.data.local.dao.BackupDao
import com.xasdify.pocketflow.data.local.dao.PresetDao
import com.xasdify.pocketflow.data.local.dao.TagDao
import com.xasdify.pocketflow.data.repository.BackupRepositoryImpl
import com.xasdify.pocketflow.data.repository.PresetRepositoryImpl
import com.xasdify.pocketflow.domain.repository.BackupRepository
import com.xasdify.pocketflow.domain.repository.PresetRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect val platformModule: Module


val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<PresetDao> { get<AppDatabase>().presetDao() }
    single<TagDao> { get<AppDatabase>().tagDao() }
    single<BackupDao> { get<AppDatabase>().backupDao() }

    singleOf(::PresetRepositoryImpl) { bind<PresetRepository>() }
    singleOf(::BackupRepositoryImpl) { bind<BackupRepository>() }
}

