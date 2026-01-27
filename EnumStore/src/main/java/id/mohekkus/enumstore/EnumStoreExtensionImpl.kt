package id.mohekkus.enumstore

import id.mohekkus.enumstore.core.EnumStore.Companion.instance
import id.mohekkus.enumstore.operation.EnumStoreOperation

interface EnumStoreExtensionImpl {

    fun <T, R: Any> using(
        marker: T,
        callback: EnumStoreOperation.() -> R
    ): R where T : Enum<T>, T : EnumStoreMarker {
        return EnumStoreOperation(
            instance.from(marker),
            instance.getHandler()
        ).callback()
    }
}