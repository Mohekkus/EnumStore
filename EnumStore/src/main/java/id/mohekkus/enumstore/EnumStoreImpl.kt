package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

interface EnumStoreImpl {
    // Synchronous
    fun <T> get(name: Preferences.Key<T>): T?
    fun getBoolean(name: String): Boolean
    fun getList(name: String): Set<String>
    fun getStrings(name: String): String

    fun <T> edit(name: Preferences.Key<T>, value: T)
    fun setBoolean(name: String, value: Boolean)
    fun setList(name: String, value: Set<String>)
    fun setStrings(name: String, value: String)

    fun <T> erase(name: Preferences.Key<T>)
    fun eraseBoolean(name: String)
    fun eraseList(name: String)
    fun eraseString(name: String)

    fun purge()
}