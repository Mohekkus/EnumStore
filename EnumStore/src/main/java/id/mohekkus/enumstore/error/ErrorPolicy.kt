package id.mohekkus.enumstore.error

import id.mohekkus.enumstore.operation.OperationType

internal sealed interface ErrorPolicy<T> {
    val type: OperationType

    fun onError(error: Throwable): T
}

internal class ReturnDefault<T>(
    override val type: OperationType,
    private val defaultValue: T
) : ErrorPolicy<T> {
    override fun onError(error: Throwable): T = defaultValue
}

internal data class ReturnBoolean(
    override val type: OperationType
) : ErrorPolicy<Boolean> {

    override fun onError(error: Throwable): Boolean = false
}

internal data class Rethrow(
    override val type: OperationType
) : ErrorPolicy<Nothing> {

    override fun onError(error: Throwable): Nothing {
        throw error
    }
}