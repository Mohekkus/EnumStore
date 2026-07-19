package id.mohekkus.enumstore.logging

import id.mohekkus.enumstore.operation.OperationType

interface EnumStoreLoggerImpl {
    fun success(operation: OperationType) {}
    fun error(
        operation: OperationType,
        throwable: Throwable
    )
}

internal object NoopLogger: EnumStoreLoggerImpl {
    override fun error(
        operation: OperationType,
        throwable: Throwable
    ) {}
}

class EnumStoreLogger: EnumStoreLoggerImpl {
    override fun error(
        operation: OperationType,
        throwable: Throwable
    ) {
        println("❌ EnumStore: $operation failed - ${throwable.message}")
    }
}