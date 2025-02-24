package org.miniabastos.kmproject

import androidx.compose.ui.window.ComposeUIViewController
import com.expenseApp.db.AppDatabase
import org.koin.core.context.startKoin
import org.miniabastos.kmproject.data.DatabaseDriverFactory
import org.miniabastos.kmproject.di.appModule

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(appModule(AppDatabase.invoke(DatabaseDriverFactory().createDriver())))
    }.koin
}