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
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal class EnumStorePreferencesRegistry(
    private val context: Context
) {
    internal lateinit var storeNameObfuscation: StoreNameObfuscation
    private val mapDatastore = ConcurrentHashMap<String, DataStore<Preferences>>()
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

    fun get(dataStoreName: String): DataStore<Preferences> =
        with(getStoreNameObfuscated(dataStoreName)) {
            mapDatastore[this] ?: register(this)
        }

    private fun getStoreNameObfuscated(dataStoreName: String): String {
        return (if (::storeNameObfuscation.isInitialized)
            storeNameObfuscation.onPlainText(dataStoreName)
                .ifEmpty { dataStoreName }
        else dataStoreName).toMD5(dataStoreName)
    }

    private fun String.toMD5(key: String): String {
        val mac = Mac.getInstance("HmacMD5")
        val secretKeySpec = SecretKeySpec((this@EnumStorePreferencesRegistry::class.qualifiedName.toString() + key).toByteArray(), "HmacMD5")
        mac.init(secretKeySpec)
        val hmacBytes = mac.doFinal(toByteArray())
        return hmacBytes.joinToString("") { String.format("%02x", it) }
    }

    private fun getPreferencesDataStoreFile(fileName: String): File = context.preferencesDataStoreFile(fileName)
}