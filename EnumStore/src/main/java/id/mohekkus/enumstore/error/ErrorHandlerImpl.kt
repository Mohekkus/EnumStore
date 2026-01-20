package id.mohekkus.enumstore.error

internal interface ErrorHandlerImpl {
    fun <T : Any> handle(
        policy: ErrorPolicy<out T>,
        operation: () -> T
    ): T
}