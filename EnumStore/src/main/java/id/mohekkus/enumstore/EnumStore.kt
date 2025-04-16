package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EnumStore(context: Context, keyName: String): BaseDatastore(context, keyName), EnumStoreImpl {

    companion object {
        private const val DEFAULT_KEY_NAME = "Settings"

        fun String.booleanKey() = booleanPreferencesKey(this)
        fun String.stringKey() = stringPreferencesKey(this)
        fun String.setKey() = stringSetPreferencesKey(this)

        private lateinit var _instance: EnumStore
        val instance: EnumStore?
            get() = if (this::_instance.isInitialized)
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

    override fun getBoolean(name: String): Boolean =
        get(name.booleanKey()) == true

    override fun getList(name: String): Set<String> =
        get(name.setKey()) ?: setOf()

    override fun getStrings(name: String): String =
        get(name.stringKey()) ?: ""

    override fun <T> edit(key: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it[key] = value
            }
        }
    }

    override fun setBoolean(name: String, value: Boolean) =
        edit(name.booleanKey(), value)

    override fun setList(name: String, value: Set<String>) =
        edit(name.setKey(), value)

    override fun setStrings(name: String, value: String) =
        edit(name.stringKey(), value)


    override fun <T> erase(name: Preferences.Key<T>) {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it.remove(name)
            }
        }
    }

    override fun eraseBoolean(name: String) =
        erase(name.booleanKey())

    override fun eraseList(name: String) =
        erase(name.setKey())

    override fun eraseString(name: String) =
        erase(name.stringKey())

    override fun purge() {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it.clear()
            }
        }
    }

}