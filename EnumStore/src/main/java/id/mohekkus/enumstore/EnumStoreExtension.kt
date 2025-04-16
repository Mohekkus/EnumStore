package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

object EnumStoreExtension {

    private val enumStore = EnumStore.instance

    fun <T> get(name: Preferences.Key<T>): T? = enumStore?.get(name)
    fun getBoolean(name: String): Boolean = enumStore?.getBoolean(name) == true
    fun getString(name: String): String = enumStore?.getStrings(name) ?: ""
    fun getList(name: String): Set<String> = enumStore?.getList(name) ?: setOf()

    fun <T> set(name: Preferences.Key<T>, value: T) = enumStore?.edit(name, value)
    fun setBoolean(name: String, value: Boolean) = enumStore?.setBoolean(name, value)
    fun setString(name: String, value: String) = enumStore?.setStrings(name, value)
    fun setList(name: String, value: Set<String>) = enumStore?.setList(name, value)

    fun <T> erase(name: Preferences.Key<T>) = enumStore?.erase(name)
    fun eraseBoolean(name: String) = enumStore?.eraseBoolean(name)
    fun eraseString(name: String) = enumStore?.eraseString(name)
    fun eraseList(name: String) = enumStore?.eraseList(name)

    fun purge() = enumStore?.purge()
}