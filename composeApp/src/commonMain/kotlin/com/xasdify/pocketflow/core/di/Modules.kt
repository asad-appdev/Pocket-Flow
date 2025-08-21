package com.xasdify.pocketflow.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.xasdify.pocketflow.core.data.database.DatabaseFactory
import com.xasdify.pocketflow.core.data.network.HttpClientFactory
import com.xasdify.pocketflow.core.data.repository.StorageRepositoryImpl
import com.xasdify.pocketflow.core.domain.StorageRepository
import com.xasdify.pocketflow.onBoarding.presentation.auth.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module


val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    //single<StorageRepository> { StorageRepositoryImpl(get()) }

    singleOf(::StorageRepositoryImpl) { bind<StorageRepository>() }
    viewModelOf(::LoginViewModel)

    //  single { get<AppDatabase>().favoriteBookDao }
}

