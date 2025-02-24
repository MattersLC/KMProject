package org.miniabastos.kmproject

import android.app.Application
import com.expenseApp.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.miniabastos.kmproject.data.DatabaseDriverFactory
import org.miniabastos.kmproject.di.appModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule(AppDatabase.invoke(DatabaseDriverFactory(this@MainApplication).createDriver())))
        }
    }
}