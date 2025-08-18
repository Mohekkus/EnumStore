@file:Suppress("unused")
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

object EnumStoreExtension {

    @Suppress("UNCHECKED_CAST")
    inline fun <reified R> getPreferenceKey(name: String): Preferences.Key<R> {
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
        defaultValue: R,
    ): R where T : Enum<T>, T : EnumStoreOption =
        instance.block(
            getPreferenceKey<R>(name)
        ) ?: defaultValue

    inline fun <reified T, reified R : Any> T.get(
        typeOf: EnumStoreType<R>,
        crossinline callback: (R) -> Unit
    ) where T : Enum<T>, T : EnumStoreOption {
        val value = instance.block(
            typeOf.getKey(name)
        ) ?: typeOf.defaultValue
        callback(value)
    }

    inline fun <reified T, reified R : Any> T.asFlow(
        typeOf: EnumStoreType<R>
    ): Flow<R> where T : Enum<T>, T : EnumStoreOption {
        return instance.async(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R : Any> T.asStateFlow(
        typeOf: EnumStoreType<R>
    ): StateFlow<R> where T : Enum<T>, T : EnumStoreOption {
        return instance.state(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }


    inline fun <reified T, reified R> T.set(
        value: R
    ) where T : Enum<T>, T : EnumStoreOption =
        instance.edit(getPreferenceKey(name), value)

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreType<R>,
    ) where T : Enum<T>, T : EnumStoreOption =
        instance.erase(typeOf.getKey(name))

}