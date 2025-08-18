package id.mohekkus.example

import android.app.Application
import android.content.Context
import id.mohekkus.enumstore.EnumStore

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EnumStore.create(applicationContext)
    }
}