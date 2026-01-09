package com.xasdify.pocketflow.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver

import com.xasdify.pocketflow.core.data.network.HttpClientFactory
import com.xasdify.pocketflow.data.local.AppDatabase
import com.xasdify.pocketflow.data.local.DatabaseFactory
import com.xasdify.pocketflow.data.local.dao.BackupDao
import com.xasdify.pocketflow.data.local.dao.LoanDao
import com.xasdify.pocketflow.data.local.dao.LoanPaymentDao
import com.xasdify.pocketflow.data.local.dao.PresetDao
import com.xasdify.pocketflow.data.local.dao.TagDao
import com.xasdify.pocketflow.data.repository.BackupRepositoryImpl
import com.xasdify.pocketflow.data.repository.PresetRepositoryImpl
import com.xasdify.pocketflow.domain.repository.BackupRepository
import com.xasdify.pocketflow.domain.repository.PresetRepository
import com.xasdify.pocketflow.loans.data.repository.LoanRepositoryImpl
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.loans.presentation.add.AddLoanViewModel
import com.xasdify.pocketflow.loans.presentation.detail.LoanDetailViewModel
import com.xasdify.pocketflow.loans.presentation.list.LoansListViewModel
import com.xasdify.pocketflow.transactions.data.dao.CategoryDao
import com.xasdify.pocketflow.transactions.data.dao.ExpenseDao
import com.xasdify.pocketflow.transactions.data.dao.IncomeDao
import com.xasdify.pocketflow.transactions.data.repository.CategoryRepository
import com.xasdify.pocketflow.transactions.data.repository.ExpenseRepository
import com.xasdify.pocketflow.transactions.data.repository.IncomeRepository
import com.xasdify.pocketflow.transactions.presentation.dashBoard.DashboardViewModel
import com.xasdify.pocketflow.transactions.presentation.expenses.ExpenseViewModel
import com.xasdify.pocketflow.transactions.presentation.income.IncomeViewModel
import com.xasdify.pocketflow.financialPlanning.data.repository.BudgetRepository
import com.xasdify.pocketflow.financialPlanning.data.repository.DebtRepository
import com.xasdify.pocketflow.financialPlanning.data.repository.GoalRepository
import com.xasdify.pocketflow.financialPlanning.presentation.budget.BudgetViewModel
import com.xasdify.pocketflow.financialPlanning.presentation.debt.DebtViewModel
import com.xasdify.pocketflow.financialPlanning.presentation.goals.GoalsViewModel
import com.xasdify.pocketflow.home.presentation.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    singleOf(::PresetRepositoryImpl) { bind<PresetRepository>() }
    singleOf(::BackupRepositoryImpl) { bind<BackupRepository>() }

    // single<LoanRepository> { LoanRepositoryImpl(get(), get()) }
    singleOf(::LoanRepositoryImpl) { bind<LoanRepository>() }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }


    single<PresetDao> { get<AppDatabase>().presetDao() }
    single<TagDao> { get<AppDatabase>().tagDao() }
    single<BackupDao> { get<AppDatabase>().backupDao() }

    single<LoanDao> { get<AppDatabase>().getLoanDao() }
    single<LoanPaymentDao> { get<AppDatabase>().getLoanPaymentDao() }
    
    // Transaction DAOs
    single<ExpenseDao> { get<AppDatabase>().expenseDao() }
    single<IncomeDao> { get<AppDatabase>().incomeDao() }
    single<CategoryDao> { get<AppDatabase>().categoryDao() }
    
    // Transaction Repositories
    single { ExpenseRepository(get()) }
    single { IncomeRepository(get()) }
    single { CategoryRepository(get()) }
    
    // Financial Planning DAOs
    single { get<AppDatabase>().budgetDao() }
    single { get<AppDatabase>().debtDao() }
    single { get<AppDatabase>().goalDao() }
    
    // Financial Planning Repositories
    single { BudgetRepository(get()) }
    single { DebtRepository(get()) }
    single { GoalRepository(get()) }

}

val viewModelModule = module {
    // Loans ViewModels
    viewModel { LoansListViewModel(get()) }
    viewModel { (loanId: Long) -> LoanDetailViewModel(get(), loanId) }
    viewModel { (editLoanId: Long?) -> AddLoanViewModel(get(), editLoanId) }
    
    // Transaction ViewModels
    viewModel { ExpenseViewModel(get()) }
    viewModel { IncomeViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    
    // Financial Planning ViewModels
    viewModel { BudgetViewModel(get()) }
    viewModel { DebtViewModel(get()) }
    viewModel { GoalsViewModel(get()) }
    
    // Home ViewModel
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
}