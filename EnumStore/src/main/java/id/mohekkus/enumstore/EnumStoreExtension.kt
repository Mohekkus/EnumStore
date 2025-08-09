package id.mohekkus.enumstore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import id.mohekkus.enumstore.EnumStore.Companion.instance

object EnumStoreExtension {

    fun <T> get(name: Preferences.Key<T>): T? = instance?.get(name)

    @Suppress("UNCHECKED_CAST")
    inline fun <reified R> getEnumPreferenceKey(name: String): Preferences.Key<R>  {
        return when (R::class) {
            String::class -> stringPreferencesKey(name)
            Boolean::class -> booleanPreferencesKey(name)
            Set::class -> stringSetPreferencesKey(name)
            Int::class -> intPreferencesKey(name)
            Double::class -> doublePreferencesKey(name)
            Long::class -> longPreferencesKey(name)
            ByteArray::class -> byteArrayPreferencesKey(name)

            else -> error("Type ${R::class} is not supported")
        } as Preferences.Key<R>
    }
    
    inline fun <reified T, reified R> T.get(
        defaultValue: R
    ): R where T : Enum<T>, T : EnumStoreOption =
        instance?.get(
            getEnumPreferenceKey<R>(name)
        ) ?: defaultValue


//    inline fun <reified T, reified R> T.set(
//        value: R
//    ) where T : Enum<T>, T : EnumStoreOption =
//        instance?.edit(getEnumStoreKey<T, R>(), value)
//
//    inline fun <reified T> T.erase() where T : Enum<T>, T : EnumStoreOption =
//        instance?.erase(getEnumStoreKey<T, R>())

}