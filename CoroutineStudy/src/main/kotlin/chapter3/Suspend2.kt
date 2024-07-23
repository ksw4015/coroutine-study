package org.example.chapter3

import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 코루틴에서 잠깐 Sleep후 재개되는 다른 스레드
 */
suspend fun main() {
    println("Before")
    suspendCoroutine<Unit> {
        continueAfterSecond(it)
    }
    println("After")
}

fun continueAfterSecond(continuation: Continuation<Unit>) {
    thread {
        println("Suspended")
        Thread.sleep(1000)
        continuation.resume(Unit)
        println("Resumed")  // Suspend.kt에 있는 예제와는 다르게 다른 Thread내부에서 실행됨으로 After후에 출력된다.
    }
}