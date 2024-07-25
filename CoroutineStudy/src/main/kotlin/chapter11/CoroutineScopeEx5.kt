package org.example.chapter11

import kotlinx.coroutines.*

/**
 * withTimeout
 * - 제한시간이 있는 coroutineScope
 * - 시간이 오버되면 TimeoutCancellationException을 던진다
 */

suspend fun test(): Int = withTimeout(1500) {
    delay(1000)
    println("Still running...")
    delay(1000)
    println("Done!")
    42
}

suspend fun main() {
    coroutineScope{
        println("Before")
        try {
            test()
        } catch (e: TimeoutCancellationException) {
            println("Timeout Cancelled")
        }
        // TimeoutCancellationException은 CancellationException의 서브타입이므로, 부모 코루틴에 영향을 주지 않는다.
        delay(1000)
        println("After")
    }
}