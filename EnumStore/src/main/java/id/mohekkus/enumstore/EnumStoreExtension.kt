@file:Suppress("unused")
package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStoreType.Companion.getKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

object EnumStoreExtension: EnumStoreExtensionImpl {

    // Artifact
    inline fun <reified T, reified R: Any> T.get(
        defaultValue: R,
    ): R where T : Enum<T>, T : EnumStoreMarker {
        return using(
            this
        ) {
            safeBlock(
                getKey(defaultValue, name),
                defaultValue
            )
        }
    }

    inline fun <reified T, reified R : Any> T.get(
        typeOf: EnumStoreType<R>,
        crossinline callback: (R) -> Unit
    ) where T : Enum<T>, T : EnumStoreMarker {
        using(
            this
        ) {
            callback(
                safeBlock(
                    typeOf.getKey(name),
                    typeOf.defaultValue
                )
            )
        }
    }

    inline fun <reified T, reified R : Any> T.asFlow(
        typeOf: EnumStoreType<R>
    ): Flow<R> where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeFlow(
                typeOf.getKey(name), typeOf.defaultValue
            )
        }


    inline fun <reified T, reified R : Any> T.asStateFlow(
        typeOf: EnumStoreType<R>
    ): StateFlow<R> where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeState(
                typeOf.getKey(name), typeOf.defaultValue
            )
        }

    inline fun <reified T, reified R: Any> T.set(
        value: R
    ) where T : Enum<T>, T : EnumStoreMarker {
        using(this) {
            safeEdit(getKey(value, name), value)
        }
    }

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreType<R>,
    ) where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeErase(typeOf.getKey(name))
        }
}