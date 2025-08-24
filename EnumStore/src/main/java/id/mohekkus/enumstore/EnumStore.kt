@file:Suppress("unused")
package id.mohekkus.enumstore

import android.content.Context
import android.provider.Telephony.Mms.Part
import androidx.annotation.WorkerThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

class EnumStore(context: Context) : BaseDatastore() {

    private val registry by lazy {
        EnumStorePreferencesRegistry(context)
    }

    companion object {
        private lateinit var _instance: EnumStore
        val EnumStoreExtension.instance: EnumStore
            get() = _instance

        fun create(context: Context) {
            if (!::_instance.isInitialized)
                _instance = EnumStore(context)
        }
    }

    fun <T> from(markedEnum: T): EnumStoreImplInternal where T: Enum<*>, T : EnumStoreMarker {
        val name = when (markedEnum) {
            is EnumStoreShared -> {
                markedEnum::class.annotations.filterIsInstance<PartOf>().firstOrNull()?.let {
                    it.collection.simpleName
                } ?: markedEnum::class.simpleName
            }
            // markedEnum.getSharedTarget()?.simpleName ?: markedEnum::class.simpleName
            else -> markedEnum::class.simpleName
        }

        return EnumStoreImplInternal(registry.get(name.toString()))
    }

    inner class EnumStoreImplInternal(private val dataStore: DataStore<Preferences>) : EnumStoreImpl {

        @WorkerThread
        override fun <T> block(name: Preferences.Key<T>): T? =
            runBlocking {
                dataStore.getDataFlow(name).firstOrNull()
            }

        override fun <T> async(name: Preferences.Key<T>, defaultValue: T): Flow<T> =
            dataStore.getDataFlow(name)
                .map { it ?: defaultValue }

        override fun <T> state(name: Preferences.Key<T>, defaultValue: T): StateFlow<T> =
            async(name, defaultValue)
                .stateIn(
                    registry.staticScope,
                    initialValue = defaultValue,
                    started = SharingStarted.Lazily
                )

        override fun <T> edit(name: Preferences.Key<T>, value: T) {
            dataStore.getMutablePreferences {
                it[name] = value
            }
        }

        override fun <T> erase(name: Preferences.Key<T>) {
            dataStore.getMutablePreferences {
                it.remove(name)
            }
        }

        override fun purge() {
            dataStore.getMutablePreferences {
                it.clear()
            }
        }
    }

}