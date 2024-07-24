package org.example.chapter10

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 5. CancellationExceotion은 부모로 전파되지 않는다.
 */

object MyNonPropagationException : CancellationException()

suspend fun main() {
    coroutineScope {
        launch { // 1
            launch { // 2
                delay(2000)
                println("Will not be printed")
            }
            throw MyNonPropagationException // 1에서 시작된 Coroutine은 자기 자신을 취소하고 2까지 취소시킨다.
        }
        launch {
            delay(2000)
            println("Will be printed")
        }
    }
}