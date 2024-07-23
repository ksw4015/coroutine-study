package org.example.chapter7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.chapter3.delay
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Custom CoroutineContext
 */

class MyCustomContext : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*>
        get() = Key

    companion object Key :
        CoroutineContext.Key<MyCustomContext>
}

class CounterContext(
    private val name: String
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*>
        get() = Key
    private var nextNumber: Int = 0

    fun printNext() {
        println("$name: $nextNumber")
        nextNumber++;
    }

    companion object Key :
        CoroutineContext.Key<CounterContext>
}

suspend fun printNext() {
    coroutineContext[CounterContext]?.printNext()
}

/*
    Output
    Outer: 0
    Outer: 1
    Outer: 2
    Outer: 3
    Inner: 0
    Inner: 1
    Inner: 2
 */
suspend fun main() {
    withContext(CounterContext("Outer")) {
        printNext()
        launch {
            printNext()
            launch {
                printNext()
            }
            launch(CounterContext("Inner")) {
                printNext()
                printNext()
                launch { printNext() }
            }
        }
        printNext()
    }
}