package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
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
        fun String.intKey() = intPreferencesKey(this)
        fun String.doubleKey() = doublePreferencesKey(this)
        fun String.longKey() = longPreferencesKey(this)
        fun String.byteArrayKey() = byteArrayPreferencesKey(this)

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

    override fun getInt(name: String): Int =
        get(name.intKey()) ?: -1

    override fun getDouble(name: String): Double =
        get(name.doubleKey()) ?: 0.0

    override fun getLong(name: String): Long =
        get(name.longKey()) ?: 0L

    override fun getByteArray(name: String): ByteArray =
        get(name.byteArrayKey()) ?: byteArrayOf()


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

    override fun setInt(name: String, value: Int) =
        edit(name.intKey(), value)

    override fun setDouble(name: String, value: Double) =
        edit(name.doubleKey(), value)

    override fun setLong(name: String, value: Long) =
        edit(name.longKey(), value)

    override fun setByteArray(name: String, value: ByteArray) =
        edit(name.byteArrayKey(), value)


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

    override fun eraseInt(name: String) =
        erase(name.intKey())

    override fun eraseDouble(name: String) =
        erase(name.doubleKey())

    override fun eraseLong(name: String) =
        erase(name.longKey())

    override fun eraseByteArray(name: String) =
        erase(name.byteArrayKey())


    override fun purge() {
        CoroutineScope(Dispatchers.IO).launch {
            setting.edit {
                it.clear()
            }
        }
    }

}