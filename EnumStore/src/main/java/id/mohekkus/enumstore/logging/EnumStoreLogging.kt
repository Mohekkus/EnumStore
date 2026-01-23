package id.mohekkus.enumstore.logging

import id.mohekkus.enumstore.operation.OperationType

abstract class EnumStoreLogging {

    internal fun <T: Any> handle(
        type: OperationType,
        operation: () -> T
    ): T {
        return try {
            operation()
        } catch (e: Exception) {
            log("Operation $type failed")
            throw e
        }
    }

    private fun log(message: String, throwable: Throwable? = null) {
        println("EnumStore: $message")
        throwable?.printStackTrace()
    }
}