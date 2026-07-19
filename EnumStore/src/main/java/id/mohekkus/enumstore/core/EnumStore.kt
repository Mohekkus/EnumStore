@file:Suppress("unused")
package id.mohekkus.enumstore.core

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import id.mohekkus.enumstore.EnumStoreMarker
import id.mohekkus.enumstore.EnumStorePreferencesRegistry
import id.mohekkus.enumstore.EnumStoreShared
import id.mohekkus.enumstore.PartOf
import id.mohekkus.enumstore.error.ErrorHandler
import id.mohekkus.enumstore.logging.EnumStoreLoggerImpl
import id.mohekkus.enumstore.logging.NoopLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class EnumStore(
    context: Context,
    logger: EnumStoreLoggerImpl
) : BaseDatastore() {

    private val handler by lazy {
        ErrorHandler(logger)
    }
    private val registry by lazy {
        EnumStorePreferencesRegistry(context)
    }

    companion object {
        private lateinit var _instance: EnumStore
        internal val instance: EnumStore
            get() = _instance

        fun create(context: Context, logger: EnumStoreLoggerImpl = NoopLogger) {
            if (!Companion::_instance.isInitialized)
                _instance = EnumStore(context, logger)
        }
    }

    internal fun getHandler() = handler

    internal fun <T> from(markedEnum: T): EnumStoreImplInternal where T: Enum<*>, T : EnumStoreMarker {
        val name = when (markedEnum) {
            is EnumStoreShared -> {
                markedEnum::class.java.annotations.filterIsInstance<PartOf>().firstOrNull()?.collection?.simpleName
                    ?: markedEnum::class.simpleName
            }
            // markedEnum.getSharedTarget()?.simpleName ?: markedEnum::class.simpleName
            else -> markedEnum::class.simpleName
        }

        return EnumStoreImplInternal(registry.get(name.toString()))
    }

    internal inner class EnumStoreImplInternal(
        private val dataStore: DataStore<Preferences>
    ) : EnumStoreImpl {

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