package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface EnumStoreImpl {
//    fun from(storageName: String): EnumStore

//    // Synchronous
    fun <T> block(name: Preferences.Key<T>): T?

    fun <T> async(name: Preferences.Key<T>, defaultValue: T): Flow<T?>

    fun <T> state(name: Preferences.Key<T>, defaultValue: T): StateFlow<T>

    fun <T> edit(name: Preferences.Key<T>, value: T)

    fun <T> erase(name: Preferences.Key<T>)

    fun purge()
}