package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface EnumStoreImpl {
    // Synchronous
    fun <T> block(name: Preferences.Key<T>): T?

    fun <T> async(name: Preferences.Key<T>): Flow<T?>

    fun <T> edit(name: Preferences.Key<T>, value: T)

    fun <T> erase(name: Preferences.Key<T>)

    fun purge()
}