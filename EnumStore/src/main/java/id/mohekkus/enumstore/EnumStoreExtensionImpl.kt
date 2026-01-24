package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStore.Companion.instance
import id.mohekkus.enumstore.operation.EnumStoreOperation

internal interface EnumStoreExtensionImpl {

    fun <T, R: Any> using(
        marker: T,
        callback: EnumStoreOperation.() -> R
    ): R where T : Enum<T>, T : EnumStoreMarker {
        return EnumStoreOperation(
            instance.from(marker)
        ).callback()
    }
}