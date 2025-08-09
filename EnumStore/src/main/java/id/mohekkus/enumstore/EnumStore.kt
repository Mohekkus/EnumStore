package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EnumStore(context: Context, storageName: String) : BaseDatastore(context, storageName),
    EnumStoreImpl {

    companion object {
        private const val DEFAULT_KEY_NAME = "Settings"

        private lateinit var _instance: EnumStore
        val EnumStoreExtension.instance: EnumStore?
            get() = if (this@Companion::_instance.isInitialized)
                null
            else _instance

        fun create(context: Context, keyName: String = DEFAULT_KEY_NAME) {
            if (!::_instance.isInitialized)
                _instance = EnumStore(context, keyName)
        }
    }

    override val setting: DataStore<Preferences>
        get() = super.setting

    override fun <T> get(name: Preferences.Key<T>): T? =
        runBlocking {
            setting.data.map {
                it[name]
            }.firstOrNull()
        }

    override fun <T> edit(key: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it[key] = value
            }
        }
    }

    override fun <T> erase(name: Preferences.Key<T>) {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it.remove(name)
            }
        }
    }

    override fun purge() {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it.clear()
            }
        }
    }

}