package org.example.chapter11

import kotlinx.coroutines.*

/**
 * withContext 함수
 * - 인자로 Context를 제공하면 부모 스코프의 Context를 대체한다.
 */

fun main() {
    runBlocking(CoroutineName("Parent")) {
        log("Before")
        withContext(CoroutineName("Child 1")) {
            delay(100)
            log("Hello")
        }
        withContext(CoroutineName("Child 2")) {
            delay(1000)
            log("Hello")
        }
        log("After")
    }
}

fun CoroutineScope.log(text: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[${name}] $text")
}