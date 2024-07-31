package org.example.chapter16

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 채널 용량이 꽉 찼을때의 행동을 정의할 수 있다.
 * onBufferOverflow
 * produce 함수로는 설정할 수 없으므로 Channel을 사용해 정의한다.
 */

suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>(
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST  // 가장 오래된 원소 제거
    )

    launch {
        repeat(5) { index ->
            channel.send(index * 2)
            delay(100)
            println("Sent")
        }
        channel.close()  // produce 함수로 생성한 것이 아니라 close() 호출
    }

    // Sent * 5
    delay(1000)
    // 1 - 0.1 * 4 초 후
    for(element in channel) {
        println(element) // 6, 8 출력
        delay(1000)
    }
}