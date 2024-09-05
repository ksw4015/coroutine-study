package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A","B")
        .onEach { delay(1000) }
        .onEach { println("Produced $it") }
    val stateFlow = flow.stateIn(
        this,
        started = SharingStarted.Lazily,
        initialValue = "Empty"
    )

    println(stateFlow.value)

    delay(2000)
    stateFlow.collect {
        println("Received $it")
    }
}