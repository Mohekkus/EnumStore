package id.mohekkus.enumstore.logging

sealed class EnumStoreExceptions(message: String, cause: Throwable? = null): Exception(message, cause) {

    class NotInitializedException(message: String): EnumStoreExceptions(message)
    class InitializationException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)

    class ReadException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class WriteException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class EraseException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class FlowException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class StateFlowException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class PurgeException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)
    class EnumStoreException(message: String, cause: Throwable) : EnumStoreExceptions(message, cause)


    class KeyNotFoundException(message: String): EnumStoreExceptions(message)
    class TypeNotFoundException(message: String): EnumStoreExceptions(message)
    class EnumNotFoundException(message: String): EnumStoreExceptions(message)
}