package org.example.chapter10

import kotlinx.coroutines.*

/**
 * 중요!
 * 6. CoroutineExceptionHandler
 *  - CoroutineExceptionHandler는 CoroutineContext다
 *  - 예외 전파를 중단 시키진 않지만, 행동을 정의할 수 있다.
 */

fun main() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(SupervisorJob() + handler)
        scope.launch {
            delay(1000)
            throw Error("Some Error") // Hadler에서 Some Error 출력
        }
        scope.launch {
            delay(2000)
            println("Will be printed")
        }

        delay(3000)
    }
}