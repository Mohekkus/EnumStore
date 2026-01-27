@file:Suppress("unused")
package id.mohekkus.enumstore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

object EnumStoreExtension: EnumStoreExtensionImpl {

    // Artifact
//    inline fun <reified T, reified R: Any> T.get(
//        defaultValue: R,
//    ): R where T : Enum<T>, T : EnumStoreMarker {
//        return using(
//            this
//        ) {
//            safeBlock(
//                getKey(defaultValue, name),
//                defaultValue
//            )
//        }
//    }

    inline fun <reified T, reified R : Any> T.get(
        typeOf: EnumStoreTypeImpl<R>,
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
        typeOf: EnumStoreTypeImpl<R>
    ): Flow<R> where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeFlow(
                typeOf.getKey(name), typeOf.defaultValue
            )
        }


    inline fun <reified T, reified R : Any> T.asStateFlow(
        typeOf: EnumStoreTypeImpl<R>
    ): StateFlow<R> where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeState(
                typeOf.getKey(name), typeOf.defaultValue
            )
        }

    inline fun <reified T, reified R: Any> T.set(
        typeOf: EnumStoreTypeImpl<R>,
        value: R
    ) where T : Enum<T>, T : EnumStoreMarker {
        using(this) {
            safeEdit(
                typeOf.getKey(name), value
            )
        }
    }

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreTypeImpl<R>,
    ) where T : Enum<T>, T : EnumStoreMarker =
        using(this) {
            safeErase(typeOf.getKey(name))
        }
}