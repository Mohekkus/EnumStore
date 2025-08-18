@file:Suppress("unused")
package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class EnumStore(context: Context, storageName: String) : BaseDatastore(context, storageName),
    EnumStoreImpl {

    companion object {
        private const val DEFAULT_KEY_NAME = "Settings"

        private lateinit var _instance: EnumStore
        val EnumStoreExtension.instance: EnumStore
            get() = _instance

        fun create(context: Context, keyName: String = DEFAULT_KEY_NAME) {
            if (!::_instance.isInitialized)
                _instance = EnumStore(context, keyName)
        }
    }

    override val setting: DataStore<Preferences>
        get() = super.setting

    fun interface EnumStoreScoped {
        suspend fun invoke()
    }

    private fun getMutablePreferences(operations: (MutablePreferences) -> Unit) {
        asyncLaunch {
            setting.edit {
                operations.invoke(it)
            }
        }
    }

    override fun <T> block(name: Preferences.Key<T>): T? =
        runBlocking {
            getDataFlow(name).firstOrNull()
        }

    override fun <T> async(name: Preferences.Key<T>, defaultValue: T): Flow<T> =
        getDataFlow(name)
            .map { it ?: defaultValue }

    override fun <T> state(name: Preferences.Key<T>, defaultValue: T): StateFlow<T> =
        async(name, defaultValue)
            .stateIn(
                CoroutineScope(Dispatchers.IO),
                initialValue = defaultValue,
                started = kotlinx.coroutines.flow.SharingStarted.Lazily
            )

    override fun <T> edit(name: Preferences.Key<T>, value: T) {
        getMutablePreferences {
            it[name] = value
        }
    }

    override fun <T> erase(name: Preferences.Key<T>) {
        getMutablePreferences {
            it.remove(name)
        }
    }

    override fun purge() {
        getMutablePreferences {
            it.clear()
        }
    }

}