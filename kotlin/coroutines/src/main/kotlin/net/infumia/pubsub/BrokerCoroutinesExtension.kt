package net.infumia.pubsub

import kotlinx.coroutines.CoroutineScope

/**
 * Extension for the [Broker] interface to integrate with coroutines.
 *
 * @param scope the [CoroutineScope] to be used with the broker.
 * @return a [BrokerCoroutines] instance.
 */
fun Broker.withCoroutines(scope: CoroutineScope): BrokerCoroutines =
    BrokerCoroutinesImpl(this, scope)
