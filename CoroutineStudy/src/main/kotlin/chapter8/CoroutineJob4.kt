package org.example.chapter8

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 1. job.complete() Output
 * - Repeat: 0
 * - Repeat: 1
 * - Repeat: 2
 * - Repeat: 3
 * - Done
 *
 * 2. job.completeExceptionally() Output
 * - Repeat: 0
 * - Repeat: 1
 * - Done
 *
 * 3. Job이 Completed되면 해당 Job에서 새로운 코루틴을 시작할 수는 없다.
 */
fun main() {
    runBlocking {
        val job = Job()
        launch(job) {
            repeat(5) { num ->
                delay(200)
                println("Repeat: $num")
            }
        }

        launch {
            delay(500)
//            job.complete() // complete 메서드는 job이 완료될때까지 기다린다
            job.completeExceptionally(Error("Some Error")) // completeExceptionally 메서드는 즉시 취소
        }
        job.join()
        launch(job) {
            println("Will not be printed")
        }

        println("Done")
    }
}