package tr.com.infumia.pubsub

import java.util.concurrent.CompletableFuture

/**
 * Registers a handler to listen for messages of a specific type.
 *
 * @param handler the function to handle incoming messages of type [T].
 * @param T the type of the message to listen for.
 * @return an [AutoCloseable] that can be used to unregister the handler.
 */
inline fun <reified T : Any> Broker.listen(noinline handler: (T) -> Unit): AutoCloseable =
    listen(T::class.java, handler)

/**
 * Sends a message and expects a response of a specific type.
 *
 * @param message the message to send.
 * @param targets the targets to send the message to.
 * @param R the type of the expected response.
 * @return a [CompletableFuture] representing the response to the message.
 */
inline fun <reified R : Any> Broker.request(message: Any, vararg targets: Target): CompletableFuture<R> =
    request(message, R::class.java, *targets)

/**
 * Registers a function to respond to messages of a specific type.
 *
 * @param handler the function to handle incoming messages of type [T] and produce a response of type [R].
 * @param T the type of the message to respond to.
 * @param R the type of the response.
 * @return an [AutoCloseable] that can be used to unregister the responder.
 */
inline fun <reified T : Any, R : Any> Broker.respond(noinline handler: (T) -> R?): AutoCloseable =
    respond(T::class.java, handler)
