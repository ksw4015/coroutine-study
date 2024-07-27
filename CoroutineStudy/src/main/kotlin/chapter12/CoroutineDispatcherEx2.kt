package org.example.chapter12

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.time.measureTime

/**
 * Coroutine Dispatcher의 한도는 서로 무관하다.
 *  - Dispatchers.IO와 Dispatchers.Default의 한도는 서로 무관하기 때문에,
 *    두 가지를 모두 최대치로 사용하는 경우 공유 스레드풀 내에 코어 개수 + 64개의 스레드가 활성화 될 것이다.
 *   - Dispatchers.IO에는 limitedParallelism 함수를 위한 특별한 작동 방식이 있다.
 *
 * 중요!
 *  - Dispatchers.Default 에서 limitedParallelism 함수는 스레드 풀의 개수를 제한 하고,
 *    Dispatchers.IO 에서 limitedParallelism 함수는 Dispatchers.IO에서 독립적인 디스패처를 만든다.
 */

suspend fun main(): Unit = coroutineScope {
    // 디스패처의 한도는 서로 무관하기때문에, 동시에 측정할 수 있다.
    launch {
        printCoroutineTimes(Dispatchers.IO)
        // Dispatchers.IO took: 2.041034499s
    }
    launch {
        val dispatcher = Dispatchers.IO.limitedParallelism(100)
        printCoroutineTimes(dispatcher)
        // LimitedDispatcher@13472d4e took: 1.036882200s
    }
}

suspend fun printCoroutineTimes(
    dispatcher: CoroutineDispatcher
) {
    val test = measureTime {
        coroutineScope {
            repeat(100) {
                launch(dispatcher) {
                    Thread.sleep(1000)
                }
            }
        }
    }
    println("$dispatcher took: $test")
}