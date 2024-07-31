package org.example.chapter16

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * 앞의 Channel 방식은 예외가 발생할 경우 다른 코루틴이 영원히 기다리게된다.
 *
 * Channel을 만드는 가장 인기있는 방식인 produce
 */

suspend fun main(): Unit = coroutineScope {
    val channel = produce {
        repeat(5) { index ->
            println("Producing Next")
            delay(1000)
            send(index * 2)
        }
    }
    for (element in channel) {
        println(element)
    }
}