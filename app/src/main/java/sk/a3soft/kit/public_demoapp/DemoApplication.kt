package sk.a3soft.kit.public_demoapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApplication : Application() {

    companion object {
        @Deprecated("For deprecated java usage only.")
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        DemoApplication.applicationContext = applicationContext
    }
}