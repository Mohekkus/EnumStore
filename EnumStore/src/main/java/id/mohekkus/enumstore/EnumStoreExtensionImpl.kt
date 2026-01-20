package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStore.Companion.instance

typealias EnumStoreImplementation = EnumStore.EnumStoreImplInternal
interface EnumStoreExtensionImpl {

    fun <T, R> EnumStoreExtension.handleSafely(
        marker: T,
        callback: EnumStoreImplementation.() -> R
    ): R where T : Enum<T>, T : EnumStoreMarker {
        return instance.from(marker).callback()
    }
}