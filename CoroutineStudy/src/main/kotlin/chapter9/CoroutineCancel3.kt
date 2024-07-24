package org.example.chapter9

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * 자원을 해제하는 또 다른 방법은
 * Jon의 InvokeOnCompletion 메서드를 호출하는 것이다.
 */

suspend fun main() {
    coroutineScope {
        val job = launch {
            delay(Random.nextLong(3000))
            println("Finished")
        }
        delay(800)
        // Job이 Completed나 Cancelled상태에 도달했을때, 호출될 핸들러
        job.invokeOnCompletion { exception: Throwable? ->
            println("Will always printed")
            // exception은 예외없이 끝난 경우 null이다
            // 취소가 된 경우 CancellationException
            println("The exception was: $exception")
        }
        delay(800)
        job.cancelAndJoin()
    }
}