package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

interface EnumStoreOption {
    companion object {
        fun <T> Enum<*>.get(name: Preferences.Key<T>): T? = EnumStoreExtension.get(name)
        fun Enum<*>.getBoolean(name: String): Boolean = EnumStoreExtension.getBoolean(name)
        fun Enum<*>.getString(name: String): String = EnumStoreExtension.getString(name)
        fun Enum<*>.getList(name: String): Set<String> = EnumStoreExtension.getList(name)
        fun Enum<*>.getInt(name: String): Int? = EnumStoreExtension.getInt(name)
        fun Enum<*>.getDouble(name: String): Double? = EnumStoreExtension.getDouble(name)
        fun Enum<*>.getLong(name: String): Long? = EnumStoreExtension.getLong(name)
        fun Enum<*>.getByteArray(name: String): ByteArray? = EnumStoreExtension.getByteArray(name)

        fun <T> Enum<*>.set(name: Preferences.Key<T>, value: T) = EnumStoreExtension.set(name, value)
        fun Enum<*>.setBoolean(name: String, value: Boolean) = EnumStoreExtension.setBoolean(name, value)
        fun Enum<*>.setString(name: String, value: String) = EnumStoreExtension.setString(name, value)
        fun Enum<*>.setList(name: String, value: Set<String>) = EnumStoreExtension.setList(name, value)
        fun Enum<*>.setInt(name: String, value: Int) = EnumStoreExtension.setInt(name, value)
        fun Enum<*>.setDouble(name: String, value: Double) = EnumStoreExtension.setDouble(name, value)
        fun Enum<*>.setLong(name: String, value: Long) = EnumStoreExtension.setLong(name, value)
        fun Enum<*>.setByteArray(name: String, value: ByteArray) = EnumStoreExtension.setByteArray(name, value)

        fun <T> Enum<*>.erase(name: Preferences.Key<T>) = EnumStoreExtension.erase(name)
        fun Enum<*>.eraseBoolean(name: String) = EnumStoreExtension.eraseBoolean(name)
        fun Enum<*>.eraseString(name: String) = EnumStoreExtension.eraseString(name)
        fun Enum<*>.eraseList(name: String) = EnumStoreExtension.eraseList(name)
        fun Enum<*>.eraseInt(name: String) = EnumStoreExtension.eraseInt(name)
        fun Enum<*>.eraseDouble(name: String) = EnumStoreExtension.eraseDouble(name)
        fun Enum<*>.eraseLong(name: String) = EnumStoreExtension.eraseLong(name)
        fun Enum<*>.eraseByteArray(name: String) = EnumStoreExtension.eraseByteArray(name)

        fun Enum<*>.purge() = EnumStoreExtension.purge()
    }
}