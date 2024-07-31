package org.example.chapter16

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * 채널의 용량을 설정할 수 있다.
 * 1. Unlimited 무제한
 * 2. Buffered 특정 크기 또는 기본값 64
 * 3. Rendezvous 랑데뷰 용량이 0인 채널로 송,수신자가 만날때만 원소를 교환
 * 4. Conflated 버퍼 크기가 1인 채널, 새로운 원소가 이전 원소를 대체
 */
suspend fun main(): Unit = coroutineScope {
    // 용량이 3인 채널
    val channel = produce(capacity = 3) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }
    // Sent Sent Sent
    delay(1000)
    for (element in channel) {
        // 채널 용량이 다 차면 기다렸다가 보낸다
        // 0 Sent
        // 2 Sent
        // 4 6 8
        println(element)
        delay(1000)
    }
}