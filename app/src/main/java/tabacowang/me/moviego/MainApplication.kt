package tabacowang.me.moviego

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import tabacowang.me.moviego.di.*

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.INFO)
            androidContext(this@MainApplication)
            modules(listOf(
                networkModule,
                apiModule,
                gsonModule,
                viewModelModule,
                repoModule
            ))
        }
    }
}