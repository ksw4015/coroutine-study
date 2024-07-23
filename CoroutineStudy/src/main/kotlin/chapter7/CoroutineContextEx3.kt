package org.example.chapter7

import kotlinx.coroutines.*
import org.example.chapter3.delay

/**
 * 1. CoroutineContext는 코루틴의 데이터를 저장, 전달하는 방법
 * 2. 부모는 자식에게 컨텍스트를 전달하고, 자식은 부모의 컨텍스트를 상속받는다.
 */
fun CoroutineScope.log(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[$name] $msg")
}

fun main() {
    // 부모로 부터 상속받은 Context 출력
    runBlocking(CoroutineName("Main")) {
        log("Started")
        val value1 = async {
            delay(500)
            log("Running async...")
            42
        }
        launch {
            delay(1000)
            log("Running launch")
        }
        log("The answer is ${value1.await()}")
    }

    // 자식에게 인자로 Context를 넘기면 부모 Context를 대체한다.
    runBlocking(CoroutineName("Main2")) {
        log("Started")
        val value1 = async(CoroutineName("Async")) {
            delay(500)
            log("Running async...")
            42
        }
        launch(CoroutineName("Launch")) {
            delay(1000)
            log("Running launch")
        }
        log("The answer is ${value1.await()}")
    }
}