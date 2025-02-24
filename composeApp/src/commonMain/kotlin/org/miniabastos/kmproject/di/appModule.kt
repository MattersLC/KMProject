package org.miniabastos.kmproject.di

import com.expenseApp.db.AppDatabase
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import org.miniabastos.kmproject.data.ExpenseManager
import org.miniabastos.kmproject.data.ExpenseRepoImplement
import org.miniabastos.kmproject.domain.ExpenseRepository
import org.miniabastos.kmproject.presentacion.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single { ExpenseManager }.withOptions { createdAtStart() }
    single<ExpenseRepository> { ExpenseRepoImplement(get(), appDatabase) }
    factory { ExpensesViewModel(get()) }
}