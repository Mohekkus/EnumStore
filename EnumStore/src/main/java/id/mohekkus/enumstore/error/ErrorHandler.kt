package id.mohekkus.enumstore.error

import id.mohekkus.enumstore.logging.EnumStoreLoggerImpl
import id.mohekkus.enumstore.logging.NoopLogger

internal class ErrorHandler(
    private val logger: EnumStoreLoggerImpl = NoopLogger
): ErrorHandlerImpl {
    override fun <T : Any> handle(
        policy: ErrorPolicy<out T>,
        operation: () -> T
    ): T {
        return try {
            operation()
        } catch (t: Throwable) {
            logger.error(policy.type, t)
            return policy.onError(t)
        }
    }

}