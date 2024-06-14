package net.infumia.pubsub

import kotlin.reflect.KClass
import kotlin.time.Duration

/**
 * Interface representing a coroutine-based message broker that supports sending, receiving, and responding to messages.
 */
interface BrokerCoroutines : AutoCloseable {
    /**
     * Initializes the message broker, performing any necessary setup or initialization tasks.
     */
    suspend fun initialize()

    /**
     * Sends a message to the specified collection of targets.
     *
     * @param message the message to send.
     * @param targets the targets to send the message to.
     */
    suspend fun send(message: Any, targets: Collection<Target>)

    /**
     * Sends a message to the specified targets.
     *
     * @param message the message to send.
     * @param targets the targets to send the message to.
     */
    suspend fun send(message: Any, vararg targets: Target)

    /**
     * Sends a message to the specified targets.
     *
     * @param message the message to send.
     * @param targets the targets to send the message to.
     */
    suspend fun send(message: Any, vararg targets: Pair<String, String>)

    /**
     * Registers a handler to listen for messages of a specific type.
     *
     * @param T the type of the message to listen for.
     * @param handler the handler to process incoming messages.
     * @return an [AutoCloseable] that can be used to unregister the handler.
     */
    suspend fun <T : Any> listen(handler: HandlerCoroutines<T>): AutoCloseable

    /**
     * Registers a handler to listen for messages of a specific type.
     *
     * @param T the type of the message to listen for.
     * @param type the KClass representing the type of the message.
     * @param handler the suspend function to process incoming messages.
     * @return an [AutoCloseable] that can be used to unregister the handler.
     */
    suspend fun <T : Any> listen(type: KClass<T>, handler: suspend (T) -> Unit): AutoCloseable

    /**
     * Sends a request and awaits a response within a specified timeout.
     *
     * @param R the type of the response.
     * @param message the request message.
     * @param responseType the KClass representing the expected response type.
     * @param timeout the duration to wait for a response.
     * @param targets the collection of targets to send the request to.
     * @return the response to the request.
     */
    suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        timeout: Duration,
        targets: Collection<Target>
    ): R

    /**
     * Sends a request and awaits a response within a specified timeout.
     *
     * @param R the type of the response.
     * @param message the request message.
     * @param responseType the KClass representing the expected response type.
     * @param timeout the duration to wait for a response.
     * @param targets the targets to send the request to.
     * @return the response to the request.
     */
    suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        timeout: Duration,
        vararg targets: Target
    ): R

    /**
     * Sends a request and awaits a response using the default timeout.
     *
     * @param R the type of the response.
     * @param message the request message.
     * @param responseType the KClass representing the expected response type.
     * @param targets the collection of targets to send the request to.
     * @return the response to the request.
     */
    suspend fun <R : Any> request(message: Any, responseType: KClass<R>, targets: Collection<Target>): R

    /**
     * Sends a request and awaits a response using the default timeout.
     *
     * @param R the type of the response.
     * @param message the request message.
     * @param responseType the KClass representing the expected response type.
     * @param targets the targets to send the request to.
     * @return the response to the request.
     */
    suspend fun <R : Any> request(message: Any, responseType: KClass<R>, vararg targets: Target): R

    /**
     * Registers a responder to handle incoming messages of a specific type and produce a response.
     *
     * @param T the type of the request message.
     * @param Y the type of the response.
     * @param responder the responder to handle incoming messages and produce responses.
     * @return an [AutoCloseable] that can be used to unregister the responder.
     */
    suspend fun <T : Any, Y : Any> respond(responder: ResponderCoroutines<T, Y>): AutoCloseable

    /**
     * Registers a function to respond to incoming messages of a specific type.
     *
     * @param T the type of the request message.
     * @param Y the type of the response.
     * @param type the KClass representing the type of the request message.
     * @param responder the suspend function to handle incoming messages and produce responses.
     * @return an [AutoCloseable] that can be used to unregister the responder.
     */
    suspend fun <T : Any, Y : Any> respond(type: KClass<T>, responder: suspend (T) -> Y?): AutoCloseable

    /**
     * Closes the message broker and releases any resources it holds.
     */
    override fun close()
}

/**
 * Registers a handler to listen for messages of a specific type.
 *
 * @param T the type of the message to listen for.
 * @param handler the suspend function to process incoming messages.
 * @return an [AutoCloseable] that can be used to unregister the handler.
 */
suspend inline fun <reified T : Any> BrokerCoroutines.listen(
    noinline handler: suspend (T) -> Unit
): AutoCloseable = listen(T::class, handler)

/**
 * Sends a request and awaits a response within a specified timeout.
 *
 * @param R the type of the response.
 * @param message the request message.
 * @param timeout the duration to wait for a response.
 * @param targets the targets to send the request to.
 * @return the response to the request.
 */
suspend inline fun <reified R : Any> BrokerCoroutines.request(
    message: Any,
    timeout: Duration,
    vararg targets: Target,
): R = request(message, R::class, timeout, *targets)

/**
 * Sends a request and awaits a response using the default timeout.
 *
 * @param R the type of the response.
 * @param message the request message.
 * @param targets the targets to send the request to.
 * @return the response to the request.
 */
suspend inline fun <reified R : Any> BrokerCoroutines.request(
    message: Any,
    vararg targets: Target
): R = request(message, R::class, *targets)

/**
 * Registers a function to respond to incoming messages of a specific type.
 *
 * @param T the type of the request message.
 * @param R the type of the response.
 * @param handler the suspend function to handle incoming messages and produce responses.
 * @return an [AutoCloseable] that can be used to unregister the responder.
 */
suspend inline fun <reified T : Any, R : Any> BrokerCoroutines.respond(
    noinline handler: suspend (T) -> R?
): AutoCloseable = respond(T::class, handler)
