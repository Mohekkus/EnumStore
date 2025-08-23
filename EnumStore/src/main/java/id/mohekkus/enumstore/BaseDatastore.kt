package id.mohekkus.enumstore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class BaseDatastore {

    private fun asyncLaunch(callback: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.invoke()
        }
    }

    protected fun DataStore<Preferences>.getMutablePreferences(operations: (MutablePreferences) -> Unit) {
        asyncLaunch {
            edit {
                operations.invoke(it)
            }
        }
    }

    protected fun <T> DataStore<Preferences>.getDataFlow(name: Preferences.Key<T>): Flow<T?> {
        return data.map {
            it[name]
        }
    }

}