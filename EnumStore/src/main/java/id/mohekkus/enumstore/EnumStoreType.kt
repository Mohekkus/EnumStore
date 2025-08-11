@file:Suppress("unused")
package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey


sealed class EnumStoreType<T : Any>(
    var defaultValue: T
) {
    abstract fun getKey(keyName: String): Preferences.Key<T>

    data object TypeString : EnumStoreType<String>("") {
        override fun getKey(keyName: String): Preferences.Key<String> =
            stringPreferencesKey(keyName)
    }

    data object TypeInt : EnumStoreType<Int>(-1) {
        override fun getKey(keyName: String): Preferences.Key<Int> =
            intPreferencesKey(keyName)
    }

    data object TypeLong : EnumStoreType<Long>(-1L) {
        override fun getKey(keyName: String): Preferences.Key<Long> =
            longPreferencesKey(keyName)
    }

    data object TypeBoolean : EnumStoreType<Boolean>(false) {
        override fun getKey(keyName: String): Preferences.Key<Boolean> =
            booleanPreferencesKey(keyName)
    }

    data object TypeDouble : EnumStoreType<Double>(-1.0) {
        override fun getKey(keyName: String): Preferences.Key<Double> =
            doublePreferencesKey(keyName)
    }

    data object TypeFloat : EnumStoreType<Float>(-1f) {
        override fun getKey(keyName: String): Preferences.Key<Float> =
            floatPreferencesKey(keyName)
    }

    data object TypeByteArray : EnumStoreType<ByteArray>(byteArrayOf()) {
        override fun getKey(keyName: String): Preferences.Key<ByteArray> =
            byteArrayPreferencesKey(keyName)
    }

    data object TypeStringSet : EnumStoreType<Set<String>>(emptySet()) {
        override fun getKey(keyName: String): Preferences.Key<Set<String>> =
            stringSetPreferencesKey(keyName)
    }
}