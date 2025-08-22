@file:Suppress("unused")
package id.mohekkus.enumstore

import id.mohekkus.enumstore.EnumStore.Companion.instance
import id.mohekkus.enumstore.EnumStoreType.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

object EnumStoreExtension {

    @Suppress("UNCHECKED_CAST")
    inline fun <reified R: Any> R.getTypeFromValue(): EnumStoreType<R> {
        return when (R::class) {
            String::class -> TypeString
            Boolean::class -> TypeBoolean
            Set::class -> TypeStringSet
            Int::class -> TypeInt
            Double::class -> TypeDouble
            Long::class -> TypeLong
            ByteArray::class -> TypeByteArray
            Float::class -> TypeFloat

            else -> error("Type ${R::class} is not supported")
        } as EnumStoreType<R>
    }

    inline fun <reified T, reified R: Any> T.get(
        defaultValue: R,
    ): R where T : Enum<T>, T : EnumStoreOption {
        val typeOf = defaultValue.getTypeFromValue()
        return instance.block(
            typeOf.getKey(name)
        ) ?: defaultValue
    }

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

    inline fun <reified T, reified R: Any> T.set(
        value: R
    ) where T : Enum<T>, T : EnumStoreOption {
        with(value.getTypeFromValue()) {
            instance.edit(getKey(name), value)
        }
    }

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreType<R>,
    ) where T : Enum<T>, T : EnumStoreOption =
        instance.erase(typeOf.getKey(name))

}