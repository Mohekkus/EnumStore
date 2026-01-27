package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences

abstract class EnumStoreTypeImpl<T: Any> {
    abstract fun getKey(keyName: String): Preferences.Key<T>
    abstract val defaultValue: T
}
