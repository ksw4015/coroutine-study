package org.example.chapter16

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 코루틴 채널
 * - 공유 책장
 * - SendChannel과 ReceiveChannel로 구성
 * - receive는 채널에 원소가 없을때 원소가 들어올 때까지 중단
 * - send는 채널의 용량이 다 찼을 때 중단
 */

suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>()
    // 불완전한 방식
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next")
            channel.send(index * 2)
        }
        channel.close()
    }

    launch {
//        repeat(5) {
//            val received = channel.receive()
//            println(received)
//        }
//        for(element in channel) {
//            println(element)
//        }
        channel.consumeEach { element ->
            println(element)
        }
    }
}