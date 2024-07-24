package org.example.chapter9

import kotlinx.coroutines.*

/**
 * 중단할 수 없는걸 중단하기 - 2
 *
 * - Job의 상태를 추적하여 취소하기
 */
suspend fun main() {
    coroutineScope {
        val job = Job()
        launch(job) {
            var num = 0
            do {
                Thread.sleep(200) // 중단점이 없는 상황을 만들기 위해 delay대신 Thread.sleep 사용
                println("Print $num")
                num++
            } while (isActive)
        }

        val job2 = Job()
        launch(job2) {
            repeat(1000) { num ->
                Thread.sleep(200)
                ensureActive() // Job이 Active 상태가 아니면 예외를 던지는 함수
                println("Printing $num")
            }
        }
        delay(1100)
        job.cancelAndJoin()
        job2.cancelAndJoin()
        println("Cancelled Successfully")
        delay(1000)
    }
}