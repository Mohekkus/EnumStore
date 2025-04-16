package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

interface EnumStoreImpl {
    // Synchronous
    fun <T> get(name: Preferences.Key<T>): T?
    fun getBoolean(name: String): Boolean
    fun getList(name: String): Set<String>
    fun getStrings(name: String): String
    fun getInt(name: String): Int
    fun getDouble(name: String): Double
    fun getLong(name: String): Long
    fun getByteArray(name: String): ByteArray

    fun <T> edit(name: Preferences.Key<T>, value: T)
    fun setBoolean(name: String, value: Boolean)
    fun setList(name: String, value: Set<String>)
    fun setStrings(name: String, value: String)
    fun setInt(name: String, value: Int)
    fun setDouble(name: String, value: Double)
    fun setLong(name: String, value: Long)
    fun setByteArray(name: String, value: ByteArray)

    fun <T> erase(name: Preferences.Key<T>)
    fun eraseBoolean(name: String)
    fun eraseList(name: String)
    fun eraseString(name: String)
    fun eraseInt(name: String)
    fun eraseDouble(name: String)
    fun eraseLong(name: String)
    fun eraseByteArray(name: String)

    fun purge()
}