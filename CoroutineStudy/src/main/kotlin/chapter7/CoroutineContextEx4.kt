package org.example.chapter7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.chapter3.delay
import kotlin.coroutines.coroutineContext

suspend fun printName() {
    // coroutineContext는 모든 suspend함수 내에서 접근이 가능하다.
    // 모든 suspend 함수는 코루틴 빌더에서 실행되기 때문
    println(coroutineContext[CoroutineName]?.name)
}

suspend fun main() {
    withContext(CoroutineName("Outer")) {
        printName() // Outer
        launch(CoroutineName("Inner")) {
            printName()  // Inner
        }
        delay(500)
        printName()
    }
}