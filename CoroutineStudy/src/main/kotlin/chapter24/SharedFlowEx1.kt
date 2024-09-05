package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * SharedFlow (MutableSharedFlow)
 *
 * 공유플로우로 메시지를 내보내면 대기하고 있는 모든 코루틴이 수신하게 된다.
 */

suspend fun main(): Unit = coroutineScope {
    val mutableSharedFlow = MutableSharedFlow<String>(replay = 0)
    // 1번 코루틴
    val job1 = launch {
        // SharedFlow를 감지 하고있는 상태
        mutableSharedFlow.collect {
            println("#1 received $it")
        }
    }
    // 2번 코루틴
    val job2 = launch {
        mutableSharedFlow.collect {
            println("#2 received $it")
        }
    }
    delay(1000)
    mutableSharedFlow.emit("Message1")
    mutableSharedFlow.emit("Message2")

    // coroutineScope에서 launch로 시작된 자식 코루틴이
    // MutableSharedFlow를 감지하고 있는 상태이므로 코루틴을 취소해야 프로그램이 종료된다.
    delay(1000)
    job1.cancel()
    job2.cancel()
    println("Finish")
}