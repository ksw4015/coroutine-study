package org.example.chapter3

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 앞서 Suspend2에서 코루틴을 중단 하는 방식으로 스레드를 이용했다.
 * 하지만, 만들어진 뒤 1초있다 사라지는 스레드는 불필요해 보인다.
 *
 * 스레드 대신 '알람 시계'를 설정하는 방식으로 코루틴을 중단하는 예제
 */

private val executor =
    Executors.newSingleThreadScheduledExecutor {
        Thread(it, "scheduler").apply {
            isDaemon = true
        }
    }

/*
    Thread를 블로킹하는 방법보다 훨씬 낫다.
 */
suspend fun delay(timeMillis: Long): Unit =
    suspendCoroutine { continuation ->
        executor.schedule({
            continuation.resume(Unit)
        }, timeMillis, TimeUnit.MILLISECONDS)
    }

suspend fun main() {
    println("Before")
    delay(1000)
    println("After")
}