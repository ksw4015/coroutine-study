package org.example.chapter16

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.RENDEZVOUS) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    // 1초 후
    for(element in channel) {
        // Sent 0
        // Sent 2
        // Sent 4
        // Sent 6
        // Sent 8
        println(element)
        delay(1000)
    }
}