package net.infumia.pubsub

import kotlin.reflect.KClass

/**
 * The interface for handling Pub/Sub messages of type [T].
 *
 * @param T the type of the message to be handled.
 */
interface HandlerCoroutines<T : Any> : (T) -> Unit {
    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the [KClass] object representing the type [T].
     */
    val type: KClass<T>
}
