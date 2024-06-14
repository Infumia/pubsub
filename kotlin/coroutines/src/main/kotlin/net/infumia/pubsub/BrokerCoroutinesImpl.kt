package net.infumia.pubsub

import kotlin.reflect.KClass
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch

internal class BrokerCoroutinesImpl(
    private val delegate: Broker,
    private val scope: CoroutineScope
) : BrokerCoroutines {
    override suspend fun initialize() {
        this.delegate.initialize()
    }

    override suspend fun send(message: Any, targets: Collection<Target>) {
        this.delegate.send(message, targets)
    }

    override suspend fun send(message: Any, vararg targets: Target) {
        this.delegate.send(message, *targets)
    }

    override suspend fun send(message: Any, vararg targets: Pair<String, String>) {
        this.delegate.send(message, targets.map { Target.of(it.first, it.second) })
    }

    override suspend fun send(message: Any) {
        this.delegate.send(message)
    }

    override suspend fun <T : Any> listen(handler: HandlerCoroutines<T>): AutoCloseable =
        this.delegate.listen(handler.type.java) { scope.launch { handler(it) } }

    override suspend fun <T : Any> listen(type: KClass<T>, handler: suspend (T) -> Unit): AutoCloseable =
        this.delegate.listen(type.java) { scope.launch { handler(it) } }

    override suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        timeout: Duration,
        targets: Collection<Target>
    ): R = this.delegate.request(message, responseType.java, timeout.toJavaDuration(), targets).await()

    override suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        timeout: Duration,
        vararg targets: Target
    ): R = this.delegate.request(message, responseType.java, timeout.toJavaDuration(), *targets).await()

    override suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        timeout: Duration,
        vararg targets: Pair<String, String>
    ): R =
        this.delegate.request(
            message,
            responseType.java,
            timeout.toJavaDuration(),
            targets.map { Target.of(it.first, it.second) }
        ).await()

    override suspend fun <R : Any> request(message: Any, responseType: KClass<R>, timeout: Duration): R =
        this.delegate.request(
            message,
            responseType.java,
            timeout.toJavaDuration()
        ).await()

    override suspend fun <R : Any> request(message: Any, responseType: KClass<R>, targets: Collection<Target>): R =
        this.delegate.request(message, responseType.java, Internal.REQUEST_TIMEOUT, targets).await()

    override suspend fun <R : Any> request(message: Any, responseType: KClass<R>, vararg targets: Target): R =
        this.delegate.request(message, responseType.java, Internal.REQUEST_TIMEOUT, *targets).await()

    override suspend fun <R : Any> request(
        message: Any,
        responseType: KClass<R>,
        vararg targets: Pair<String, String>
    ): R =
        this.delegate.request(
            message,
            responseType.java,
            Internal.REQUEST_TIMEOUT,
            targets.map { Target.of(it.first, it.second) }
        ).await()

    override suspend fun <R : Any> request(message: Any, responseType: KClass<R>): R =
        this.delegate.request(
            message,
            responseType.java,
            Internal.REQUEST_TIMEOUT
        ).await()

    override suspend fun <T : Any, Y: Any> respond(responder: ResponderCoroutines<T, Y>): AutoCloseable =
        this.delegate.respond(responder.type.java) { scope.launch { responder(it) } }

    override suspend fun <T : Any, Y : Any> respond(type: KClass<T>, responder: suspend (T) -> Y?): AutoCloseable =
        this.delegate.respond(type.java) { scope.launch { responder(it) } }

    override fun close() {
        this.delegate.close()
    }
}
