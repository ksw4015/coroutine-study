package org.example.chapter16

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * Channel.CONFLATED
 *
 * 최근 원소만 받기때문에 이전 원소는 유실된다.
 */

suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.CONFLATED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    // Sent * 5
    delay(1000)
    // 1 - 4 * 0.1 초 후
    for(element in channel) {
        println(element)  // 8 (0,2,4,6 은 출력안됨)
        delay(1000)
    }
}