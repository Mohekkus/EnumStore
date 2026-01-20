package id.mohekkus.enumstore.operation

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

internal interface EnumStoreOperationImpl {

    fun <T: Any> safeBlock(key: Preferences.Key<T>, defaultValue: T): T
    fun <T: Any> safeFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    fun <T: Any> safeState(key: Preferences.Key<T>, defaultValue: T): StateFlow<T>
    fun <T: Any> safeEdit(key: Preferences.Key<T>, value: T): Boolean
    fun <T: Any> safeErase(key: Preferences.Key<T>): Boolean
    fun safePurge(): Boolean
}