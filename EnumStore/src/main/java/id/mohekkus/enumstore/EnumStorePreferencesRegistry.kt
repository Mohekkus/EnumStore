package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

internal class EnumStorePreferencesRegistry(
    private val context: Context
) {
    private val mapDatastore = mutableMapOf<String, DataStore<Preferences>>()
    internal val staticScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private fun register(key: String): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = staticScope
        ) {
            getPreferencesDataStoreFile(key)
        }.also {
            mapDatastore[key] = it
        }
    }

    fun get(dataStoreName: String): DataStore<Preferences> = mapDatastore[dataStoreName] ?: register(dataStoreName)

    private fun getPreferencesDataStoreFile(fileName: String): File = context.preferencesDataStoreFile(fileName)
}