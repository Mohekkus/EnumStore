package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

interface EnumStoreOption {
    companion object {
        fun <T> Enum<*>.get(name: Preferences.Key<T>): T? = EnumStoreExtension.get(name)
        fun Enum<*>.getBoolean(name: String): Boolean = EnumStoreExtension.getBoolean(name)
        fun Enum<*>.getString(name: String): String = EnumStoreExtension.getString(name)
        fun Enum<*>.getList(name: String): Set<String> = EnumStoreExtension.getList(name)

        fun <T> Enum<*>.set(name: Preferences.Key<T>, value: T) = EnumStoreExtension.set(name, value)
        fun Enum<*>.setBoolean(name: String, value: Boolean) = EnumStoreExtension.setBoolean(name, value)
        fun Enum<*>.setString(name: String, value: String) = EnumStoreExtension.setString(name, value)
        fun Enum<*>.setList(name: String, value: Set<String>) = EnumStoreExtension.setList(name, value)

        fun <T> Enum<*>.erase(name: Preferences.Key<T>) = EnumStoreExtension.erase(name)
        fun Enum<*>.eraseBoolean(name: String) = EnumStoreExtension.eraseBoolean(name)
        fun Enum<*>.eraseString(name: String) = EnumStoreExtension.eraseString(name)
        fun Enum<*>.eraseList(name: String) = EnumStoreExtension.eraseList(name)

        fun Enum<*>.purge() = EnumStoreExtension.purge()
    }
}