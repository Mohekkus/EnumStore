package id.mohekkus.example

import android.app.Application
import id.mohekkus.enumstore.core.EnumStore

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EnumStore.create(applicationContext)
    }
}