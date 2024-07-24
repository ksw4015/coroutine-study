package org.example.chapter9

import kotlinx.coroutines.*

/**
 * - 코루틴 취소 후 자원 정리 등 후처리
 * - 후처리 과정에 딱히 제한은 없으나, 정리과정 중 중단이나 또다른 코루틴을 시작하는 것은 불가능
 */
suspend fun main() {
    coroutineScope {
        val job = Job()
        launch(job) {
            try {
                delay(2000)
            } finally {
                println("Finally")
                // 코루틴의 중단은 내부적으로 예외를 사용해 취소된다.
                // 따라서, finally 블록안에서 모든 것을 정리한다.
                launch {
                    // 중단 상태(Cancelling)에서는 다른 코루틴을 실행하는 것이 불가능하기 때문에 무시된다.
                    println("Will not be printed")
                }

//                delay(1000)  // 여기서도 예외 발생 (중단 불가)
//                println("Will not be printed")

                // 위의 코드를 예외없이 호출해야 하는경우
                withContext(NonCancellable) {
                    delay(1000)  // 여기서도 예외 발생 (중단 불가)
                    println("Cleanup done")
                }
            }
        }
        delay(1000)
        job.cancelAndJoin()
        println("Cancel done")
    }
}