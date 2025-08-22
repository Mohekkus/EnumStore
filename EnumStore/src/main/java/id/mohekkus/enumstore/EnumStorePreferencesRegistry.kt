package id.mohekkus.enumstore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal object EnumStorePreferencesRegistry {

    private lateinit var fileInterface: EnumStorePreferenceInterface
    private val mapDatastore = mutableMapOf<String, DataStore<Preferences>>()

    fun setInterface(preferenceInterface: EnumStorePreferenceInterface) {
        fileInterface = preferenceInterface
    }

    fun register(key: String): DataStore<Preferences> {
        mapDatastore[key] = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        ) {
            fileInterface.onGeneratingFile(key)
        }.also {
            return it
        }
    }

    fun get(key: String): DataStore<Preferences> = mapDatastore[key] ?: register(key)
}