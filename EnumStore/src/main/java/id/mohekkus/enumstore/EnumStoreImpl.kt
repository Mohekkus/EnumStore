package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

interface EnumStoreImpl {
    // Synchronous
    fun <T> get(name: Preferences.Key<T>): T?

    fun <T> edit(name: Preferences.Key<T>, value: T)

    fun <T> erase(name: Preferences.Key<T>)

    fun purge()
}