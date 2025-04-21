package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

object EnumStoreExtension {

    private val enumStore = EnumStore.instance

    fun <T> get(name: Preferences.Key<T>): T? = enumStore?.get(name)
    fun getBoolean(name: String): Boolean = enumStore?.getBoolean(name) == true
    fun getList(name: String): Set<String> = enumStore?.getList(name) ?: setOf()
    fun getString(name: String): String = enumStore?.getStrings(name) ?: ""
    fun getInt(name: String): Int? = enumStore?.getInt(name)
    fun getDouble(name: String): Double? = enumStore?.getDouble(name)
    fun getLong(name: String): Long? = enumStore?.getLong(name)
    fun getByteArray(name: String): ByteArray? = enumStore?.getByteArray(name)

    fun <T> set(name: Preferences.Key<T>, value: T) = enumStore?.edit(name, value)
    fun setBoolean(name: String, value: Boolean) = enumStore?.setBoolean(name, value)
    fun setList(name: String, value: Set<String>) = enumStore?.setList(name, value)
    fun setString(name: String, value: String) = enumStore?.setStrings(name, value)
    fun setInt(name: String, value: Int) = enumStore?.setInt(name, value)
    fun setDouble(name: String, value: Double) = enumStore?.setDouble(name, value)
    fun setLong(name: String, value: Long) = enumStore?.setLong(name, value)
    fun setByteArray(name: String, value: ByteArray) = enumStore?.setByteArray(name, value)

    fun <T> erase(name: Preferences.Key<T>) = enumStore?.erase(name)
    fun eraseBoolean(name: String) = enumStore?.eraseBoolean(name)
    fun eraseList(name: String) = enumStore?.eraseList(name)
    fun eraseString(name: String) = enumStore?.eraseString(name)
    fun eraseInt(name: String) = enumStore?.eraseInt(name)
    fun eraseDouble(name: String) = enumStore?.eraseDouble(name)
    fun eraseLong(name: String) = enumStore?.eraseLong(name)
    fun eraseByteArray(name: String) = enumStore?.eraseByteArray(name)

    fun purge() = enumStore?.purge()
}