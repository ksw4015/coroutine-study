package org.example.chapter19

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun userFlow(): Flow<String> = flow {
    repeat(3) {
        delay(1000)
        val ctx = currentCoroutineContext()
        val name = ctx[CoroutineName]?.name
        emit("User$it in $name")
    }
}

suspend fun main() {
    val users = userFlow()
    withContext(CoroutineName("Name")) {
        val job = launch {
            users.collect {
                println(it)
            }
        }
        launch {
            delay(2500)
            println("I got enough")
            job.cancel()
            // User2 in Name은 출력되지 않음
        }
    }

    flow {
        emit("Message 1")
    }
        .onEach { println(it) }
        .onStart { println("Do something before") }
        .onCompletion { println("Do something after") }
        .catch { emit("Error") }
        .collect {
            println("Collected $it")
        }
}