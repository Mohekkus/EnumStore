package id.mohekkus.enumstore.operation

import androidx.datastore.preferences.core.Preferences
import id.mohekkus.enumstore.core.EnumStoreImpl
import id.mohekkus.enumstore.error.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class EnumStoreOperation internal constructor(
    private val impl: EnumStoreImpl,
    private val errorHandler: ErrorHandler
) : EnumStoreOperationImpl {

    override fun <T : Any> safeBlock(
        key: Preferences.Key<T>,
        defaultValue: T
    ): T = errorHandler.handle(
        ReturnDefault(
            OperationType.READ,
            defaultValue
        )
    ) {
        impl.block(key) ?: defaultValue
    }

    override fun <T : Any> safeFlow(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        errorHandler.handle(
            Rethrow(OperationType.FLOW)
        ) {
            impl.async(key, defaultValue)
        }

    override fun <T : Any> safeState(key: Preferences.Key<T>, defaultValue: T): StateFlow<T> =
        errorHandler.handle(
            Rethrow(OperationType.STATE)
        ) {
            impl.state(key, defaultValue)
        }

    override fun <T : Any> safeEdit(
        key: Preferences.Key<T>,
        value: T
    ) =
        errorHandler.handle(
            ReturnBoolean(
                OperationType.WRITE
            )
        ) {
            impl.edit(key, value)
            true
        }

    override fun <T : Any> safeErase(key: Preferences.Key<T>) =
        errorHandler.handle(
            ReturnBoolean(
                OperationType.ERASE
            )
        ) {
            impl.erase(key)
            true
        }

    override fun safePurge() =
        errorHandler.handle(
            ReturnBoolean(
                OperationType.PURGE
            )
        ) {
            impl.purge()
            true
        }
}