package com.kotlin.arch

import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consume
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

fun <E> ReceiveChannel<E>.launchConsumeEach(context: CoroutineContext = DefaultDispatcher,
                                            start: CoroutineStart = CoroutineStart.DEFAULT,
                                            parent: Job? = null,
                                            action: (E) -> Unit) = launch(context, start, parent) {
    consume {
        for (element in this) action(element)
    }
}