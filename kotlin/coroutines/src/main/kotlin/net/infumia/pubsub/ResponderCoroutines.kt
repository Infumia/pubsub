package net.infumia.pubsub

import kotlin.reflect.KClass

/**
 * The interface for handling Pub/Sub messages of type [T] and providing a response of type [Y].
 *
 * @param T the type of the message to be handled.
 * @param Y the type of the response.
 */
interface ResponderCoroutines<T : Any, Y : Any> : (T) -> Y? {
    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the [KClass] object representing the type [T].
     */
    val type: KClass<T>
}
