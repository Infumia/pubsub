package tr.com.infumia.pubsub.kotlin

import java.lang.AutoCloseable
import java.util.concurrent.CompletableFuture
import tr.com.infumia.pubsub.Broker
import tr.com.infumia.pubsub.Target

inline fun <reified R : Any> Broker.request(
    message: Any,
    vararg targets: Target,
): CompletableFuture<R> = request(message, R::class.java, *targets)

inline fun <reified T : Any> Broker.listen(noinline handler: (T) -> Unit): AutoCloseable =
    listen(T::class.java, handler)

inline fun <reified T : Any, R : Any> Broker.respond(noinline handler: (T) -> R?): AutoCloseable =
    respond(T::class.java, handler)
