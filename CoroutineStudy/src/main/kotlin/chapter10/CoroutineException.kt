package org.example.chapter10

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Coroutine 예외처리
 * 1. Thread와의 차이점
 *  - 자식 Coroutine에서 예외 발생 시 부모 Coroutine 종료
 *  - 부모 Coroutine에서 예외 발생 시 모든 자식 Coroutine 종료
 */

fun main() {
    runBlocking {
        // 코루틴 빌더(runBlocking) 내부에서
        // 또 다른 코루틴 빌더(launch)를 try ~ catch로 감싸는건 도움이 되지 않는다.
        try {
            launch {
                delay(1000)
                throw Error("Some Error")
            }
        } catch (e: Throwable) {
            println("Will not be printed") // 무시됨
        }
        // 실행되지 않음
        launch {
            delay(2000)
            println("Will not be printed")
        }
    }
}

