package org.miniabastos.kmproject.di

import com.expenseApp.db.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import org.miniabastos.kmproject.data.ExpenseManager
import org.miniabastos.kmproject.data.ExpenseRepoImplement
import org.miniabastos.kmproject.domain.ExpenseRepository
import org.miniabastos.kmproject.presentacion.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single<HttpClient> { HttpClient { install(ContentNegotiation) { json() } } }
    single<ExpenseRepository> { ExpenseRepoImplement(appDatabase, get()) }
    factory { ExpensesViewModel(get()) }
}