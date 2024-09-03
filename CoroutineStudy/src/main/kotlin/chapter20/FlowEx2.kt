package org.example.chapter20

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlin.random.Random

suspend fun main(): Unit = coroutineScope {
    val f1 = List(1000) {
        "$it"
    }.asFlow()
    val f2 = List(1000) {
        "$it"
    }.asFlow().counter()

    launch {
        println(f1.counter().last())
        println(f1.counter().last())
    }
    launch {
        println(f2.last())
        println(f2.last())
    }
}

fun Flow<*>.counter() = flow {
    var conter = 0
    collect {
        conter++
        List(100) {
            Random.nextLong()
        }.shuffled().sorted()
        emit(conter)
    }
}