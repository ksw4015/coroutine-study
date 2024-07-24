package org.example.chapter9

import kotlinx.coroutines.*

/**
 * 중단할 수 없는걸 중단하기 - 1
 *
 * 코루틴내에서 중단점이 없는 경우 취소되지 않는다.
 * - yield메서드나 Dispatcher를 사용해 스레드를 바꾸는 등 중단점을 만들어서 취소
 */
suspend fun main() {
    coroutineScope {
        val job = Job()
        launch(job) {
            repeat(1000) { num ->
                Thread.sleep(200) // 중단점이 없는 상황을 만들기 위해 delay대신 Thread.sleep 사용
                yield() // 코루틴을 중단하고 즉시 재실행한다. (중단점 생성)
                println("Print $num")
            }
        }
        delay(1100)
        job.cancelAndJoin()
        println("Cancelled Successfully")
        delay(1000)
    }
}