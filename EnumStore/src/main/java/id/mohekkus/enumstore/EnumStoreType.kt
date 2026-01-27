@file:Suppress("unused")
package id.mohekkus.enumstore

import androidx.datastore.preferences.core.*

sealed interface EnumStoreType {

    data class TypeString(
        override val defaultValue: String = ""
    ) : EnumStoreTypeImpl<String>() {
        override fun getKey(keyName: String): Preferences.Key<String> =
            stringPreferencesKey(keyName)
    }

    data class TypeInt(
        override val defaultValue: Int = -1
    ) : EnumStoreTypeImpl<Int>() {
        override fun getKey(keyName: String): Preferences.Key<Int> =
            intPreferencesKey(keyName)
    }

    data class TypeLong(
        override val defaultValue: Long = -1L
    ) : EnumStoreTypeImpl<Long>() {
        override fun getKey(keyName: String): Preferences.Key<Long> =
            longPreferencesKey(keyName)
    }

    data class TypeBoolean(
        override val defaultValue: Boolean = false
    ) : EnumStoreTypeImpl<Boolean>() {
        override fun getKey(keyName: String): Preferences.Key<Boolean> =
            booleanPreferencesKey(keyName)
    }

    data class TypeDouble(
        override val defaultValue: Double = -1.0
    ) : EnumStoreTypeImpl<Double>() {
        override fun getKey(keyName: String): Preferences.Key<Double> =
            doublePreferencesKey(keyName)
    }

    data class TypeFloat(
        override val defaultValue: Float = -1f
    ) : EnumStoreTypeImpl<Float>() {
        override fun getKey(keyName: String): Preferences.Key<Float> =
            floatPreferencesKey(keyName)
    }

    data class TypeByteArray(
        override val defaultValue: ByteArray = byteArrayOf()
    ) : EnumStoreTypeImpl<ByteArray>() {
        override fun getKey(keyName: String): Preferences.Key<ByteArray> =
            byteArrayPreferencesKey(keyName)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TypeByteArray

            return defaultValue.contentEquals(other.defaultValue)
        }

        override fun hashCode(): Int {
            return defaultValue.contentHashCode()
        }
    }

    data class TypeStringSet(
        override val defaultValue: Set<String> = emptySet()
    ) : EnumStoreTypeImpl<Set<String>>() {
        override fun getKey(keyName: String): Preferences.Key<Set<String>> =
            stringSetPreferencesKey(keyName)
    }
}