package org.example.chapter18

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

/**
 * 핫 and 콜드
 *
 * 핫: 채널
 * 콜드: 플로우
 */
suspend fun main() {
    coroutineScope {
        launch {
            execFlow()
        }
    }
}

suspend fun execFlow() {
    val flow = makeFlow()
    delay(1000)
    println("Calling flow...")
    flow.collect {
        value -> println(value)
    }
    println("Consuming again...")
    flow.collect {
        value -> println(value)
    }
}

// 기본적인 flow builder
// flow는 CoroutineScope가 필요하지 않음.
fun makeFlow() = flow {
    println("Flow started")
    (1..3).forEach {
        delay(1000)
        emit(it)
    }
}