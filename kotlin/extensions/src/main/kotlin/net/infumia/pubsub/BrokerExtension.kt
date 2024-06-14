package net.infumia.pubsub

import java.util.concurrent.CompletableFuture


/**
 * Sends a message to the specified targets.
 *
 * @param message the message to send.
 * @param targets the targets to send the message to.
 */
fun Broker.send(message: Any, vararg targets: Pair<String, String>) {
    this.send(message, targets.map { Target.of(it.first, it.second) })
}

/**
 * Registers a handler to listen for messages of a specific type.
 *
 * @param handler the function to handle incoming messages of type [T].
 * @param T the type of the message to listen for.
 * @return an [AutoCloseable] that can be used to unregister the handler.
 */
inline fun <reified T : Any> Broker.listen(noinline handler: (T) -> Unit): AutoCloseable =
    this.listen(T::class.java, handler)

/**
 * Sends a message and expects a response of a specific type.
 *
 * @param message the message to send.
 * @param targets the targets to send the message to.
 * @param R the type of the expected response.
 * @return a [CompletableFuture] representing the response to the message.
 */
inline fun <reified R : Any> Broker.request(message: Any, vararg targets: Target): CompletableFuture<R> =
    this.request(message, R::class.java, *targets)

/**
 * Sends a message and expects a response of a specific type.
 *
 * @param message the message to send.
 * @param targets the targets to send the message to.
 * @param R the type of the expected response.
 * @return a [CompletableFuture] representing the response to the message.
 */
inline fun <reified R : Any> Broker.request(message: Any, vararg targets: Pair<String, String>): CompletableFuture<R> =
    this.request(message, R::class.java, targets.map { Target.of(it.first, it.second) })

/**
 * Registers a function to respond to messages of a specific type.
 *
 * @param handler the function to handle incoming messages of type [T] and produce a response of type [R].
 * @param T the type of the message to respond to.
 * @param R the type of the response.
 * @return an [AutoCloseable] that can be used to unregister the responder.
 */
inline fun <reified T : Any, R : Any> Broker.respond(noinline handler: (T) -> R?): AutoCloseable =
    this.respond(T::class.java, handler)
