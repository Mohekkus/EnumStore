@file:Suppress("unused")
package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStore.Companion.instance
import id.mohekkus.enumstore.EnumStoreType.Companion.getTypeFromValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

object EnumStoreExtension {

    inline fun <reified T, reified R: Any> T.get(
        defaultValue: R,
    ): R where T : Enum<T>, T : EnumStoreCollection {
        with(defaultValue.getTypeFromValue()) {
            return instance.from(this@get).block(
                getKey(name)
            ) ?: defaultValue
        }
    }

    inline fun <reified T, reified R : Any> T.get(
        typeOf: EnumStoreType<R>,
        crossinline callback: (R) -> Unit
    ) where T : Enum<T>, T : EnumStoreCollection {
        callback(
            instance.from(this).block(
                typeOf.getKey(name)
            ) ?: typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R : Any> T.asFlow(
        typeOf: EnumStoreType<R>
    ): Flow<R> where T : Enum<T>, T : EnumStoreCollection {
        return instance.from(this).async(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R : Any> T.asStateFlow(
        typeOf: EnumStoreType<R>
    ): StateFlow<R> where T : Enum<T>, T : EnumStoreCollection {
        return instance.from(this).state(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R: Any> T.set(
        value: R
    ) where T : Enum<T>, T : EnumStoreCollection {
        with(value.getTypeFromValue()) {
            instance.from(this@set)
                .edit(getKey(name), value)
        }
    }

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreType<R>,
    ) where T : Enum<T>, T : EnumStoreCollection =
        instance.from(this)
            .erase(typeOf.getKey(name))

}