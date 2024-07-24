package org.example.chapter8

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val name = CoroutineName("Some Name")
        val job = Job()
        launch(name + job) {
            val childName = coroutineContext[CoroutineName]
            println("CoroutineName은 상속되는 Context! " + (childName == name))
            val childJob = coroutineContext[Job]
            // Job은 상속되지 않는 유일한 CoroutineContext
            println(childJob == job) // false
            println(childJob == job.children.first()) // true
        }
    }
}