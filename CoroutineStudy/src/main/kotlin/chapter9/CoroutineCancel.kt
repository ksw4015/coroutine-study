package org.example.chapter9

import kotlinx.coroutines.*

suspend fun main() {
    coroutineScope {
        val job = launch {
            repeat(1000) { i ->
                delay(100)
                Thread.sleep(100)
                println("Printing $i")
            }
        }
        delay(1000)
//        job.cancel()
        // join을 호출하지 않으면 다음작업 진행시 경쟁상태에 빠질 수 있다.
//        job.join()  // 취소를 기다리기위해 사용 Cancelling -> Cancelled
        job.cancelAndJoin()  // cancel -> join을 함께호출 하는 확장함수 제공
        println("Cancelled")
    }
}