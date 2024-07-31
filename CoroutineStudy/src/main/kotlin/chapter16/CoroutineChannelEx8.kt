package org.example.chapter16

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Fan-in
 * 여러개의 코루틴이 하나의 채널로 원소를 전송
 */

suspend fun sendString(
    channel: Channel<String>,
    text: String,
    time: Long
) {
    while (true) {
        delay(time)
        channel.send(text)
    }
}

fun main() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(50) { index ->
        println("$index ${channel.receive()}")
    }
    coroutineContext.cancelChildren()
}

