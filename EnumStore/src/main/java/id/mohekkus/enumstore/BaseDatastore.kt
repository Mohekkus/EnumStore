package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.MutablePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class BaseDatastore(context: Context, keyName: String) {

    private val Context._datastore: DataStore<Preferences> by preferencesDataStore(keyName)
    open val setting = context._datastore

    protected fun asyncLaunch(callback: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.invoke()
        }
    }

    protected fun <T> getDataFlow(name: Preferences.Key<T>): Flow<T?> {
        return setting.data.map {
            it[name]
        }
    }

}