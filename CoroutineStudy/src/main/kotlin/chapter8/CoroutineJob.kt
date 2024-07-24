package org.example.chapter8

import kotlinx.coroutines.*

/**
 * Coroutine Job Context.
 * - Job은 수명을 가지고있고, 취고가 가능하다.
 * - Job의 수명은 상태로 나타낼 수 있다.
 *   (NEW, ACTIVE, COMPLETING, CANCELLING, COMPLETED, CANCELED)
 */
suspend fun main() {
    coroutineScope {
        val job = Job()
        println(job) // JobImpl{Active}
        job.complete() // complete() 메서드로 완료 처리
        println(job) // jobImpl{Completed}

        val activeJob = launch {
            delay(1000L)
        }
        println(activeJob)
        // activeJob이 완료될 때까지 기다렸다가 실행
        activeJob.join()
        println(activeJob)

        // New상태로 지연시키는 Job
        val lazyJob = launch(start = CoroutineStart.LAZY) {
            delay(1000)
        }
        println(lazyJob) // lazyJob을 시작하지 않으면 계속 중단되어있다.
        lazyJob.start() // 실행
        println(lazyJob)
        lazyJob.join()
        println(lazyJob)
    }
}