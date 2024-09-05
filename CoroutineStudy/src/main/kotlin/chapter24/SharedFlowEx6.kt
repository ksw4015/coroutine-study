package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * WhileSubscribed(): 첫 번째 구독자가 나올 때 감지 시작
 * - 마지막 구독자가 사라지면 플로우 중지
 * - 새로운 구독자가 나오면 재시작
 */

// stopTimeout: 마지막 구독자가 사라진 뒤 감지할 시간
// replayExpirationMillis: 플로우가 멈춘 뒤 리플레이 값을 가지고있는 시간

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A","B","C","D")
        .onStart { println("Started") }
        .onCompletion { println("Finished") }
        .onEach { delay(1000) }

    val sharedFlow = flow.shareIn(
        scope = this,
        started = SharingStarted.WhileSubscribed()
    )

    delay(3000)
    // 3초 중지 후 flow 시작
    launch {
        println("#1 ${sharedFlow.first()}") // 1초 후 #1 A
    }
    launch {
        println("#2 ${sharedFlow.take(2).toList()}")  // 2초 후 #2 [A, B]
    }

    delay(3000)
    launch {
        // 새로운 구독자가 나와서 재시작
        println("#3 ${sharedFlow.first()}")  // 4초 후 #3 A
    }
}
