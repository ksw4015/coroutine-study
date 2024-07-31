package org.example.chapter16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Fan-out
 *
 * 여러 개의 코루틴이 하나의 채널로부터 원소를 받을수 있다.
 */

fun CoroutineScope.produceNumbers() = produce {
    repeat(10) {
        delay(100)
        send(it)
    }
}

fun CoroutineScope.launchProcessor(
    id: Int,
    channel: ReceiveChannel<Int>
) = launch {
    for(msg in channel) {
        println("$id received $msg")
    }
}

suspend fun main(): Unit = coroutineScope {
    val channel = produceNumbers()
    repeat(3) { id ->
        // 채널은 원소를 기다리는 코루틴들을 큐의 형태로 가지고 있다.
        // 순차적으로 출력
        // 0 received 0
        // 1 received 1
        // 2 received 2
        // 0 received 3
        // 1 received 4
        // 2 received 5
        launchProcessor(id, channel)
    }
}