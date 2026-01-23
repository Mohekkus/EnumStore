package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStore.Companion.instance
import id.mohekkus.enumstore.operation.EnumStoreOperation

interface EnumStoreExtensionImpl {

    fun <T, R: Any> EnumStoreExtension.execute(
        marker: T,
        callback: EnumStoreOperation.() -> R
    ): R where T : Enum<T>, T : EnumStoreMarker {
        return EnumStoreOperation(
            instance.from(marker)
        ).callback()
    }
}