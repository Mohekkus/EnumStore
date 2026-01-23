package id.mohekkus.enumstore.operation

import androidx.datastore.preferences.core.Preferences
import id.mohekkus.enumstore.EnumStoreImpl
import id.mohekkus.enumstore.logging.EnumStoreLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class EnumStoreOperation : EnumStoreLogging, EnumStoreOperationImpl {

    internal constructor (
        impl: EnumStoreImpl
    ) { this.impl = impl }

    private val impl: EnumStoreImpl

    override fun <T : Any> safeBlock(
        key: Preferences.Key<T>,
        defaultValue: T
    ): T = handle(OperationType.READ) {
        impl.block(key) ?: defaultValue
    }

    override fun <T : Any> safeFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        handle(OperationType.FLOW) {
            impl.async(key, defaultValue)
        }

    override fun <T : Any> safeState(key: Preferences.Key<T>, defaultValue: T): StateFlow<T> =
        handle(OperationType.STATE) {
            impl.state(key, defaultValue)
        }

    override fun <T : Any> safeEdit(
        key: Preferences.Key<T>,
        value: T
    ) =
        handle(OperationType.WRITE) {
            impl.edit(key, value)
        }

    override fun <T : Any> safeErase(key: Preferences.Key<T>) =
        handle(OperationType.ERASE) {
            impl.erase(key)
        }

    override fun safePurge() =
        handle(OperationType.PURGE) {
            impl.purge()
        }
}