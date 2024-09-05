package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

/**
 * stateIn
 *
 * Flow<T> -> StateFlow<T>
 *
 * StateFlow는 항상 값을 가져야하기 때문에
 * 값을 명시하지 않았을 때는 첫 번째 값이 계산될 때까지 기다려야한다.
 */

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A","B","C")
        .onEach { delay(1000) }
        .onEach { println("Produced $it") }
    val stateFlow = flow.stateIn(this)

    println("Listening...")
    println(stateFlow.value)
    stateFlow.collect {
        println("Received $it")
    }
}