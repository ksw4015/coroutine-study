package org.example.chapter19

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * 시퀀스에서는 yield, yieldAll외에 다른 중단 함수(delay 등)을 사용할 수 없다.
 * 따라서 시퀀스에서 delay기능을 하려면 Thread.sleep을 사용할 수 밖에 없는데,
 * 이렇게 될 경우 같은 스레드에서 launch로 시작된 다른 코루틴도 대기를 하게된다.
 *
 * 이런 상황에서는 시퀀스 대신 플로우를 사용해야한다.
 */
fun getSequence(): Sequence<String> = sequence {
    repeat(3) {
        Thread.sleep(1000)
        yield("User$it")
    }
}

fun getFlow(): Flow<String> = flow {
    repeat(3) {
        delay(1000)
        emit("User$it")
    }
}

suspend fun main() {
    withContext(newSingleThreadContext("main")) {
        launch {
            repeat(3) {
                delay(200)
                println("Processing on coroutine")
            }
        }

//        val list = getSequence()
//        list.forEach { println(it) }
        val list = getFlow()
        list.collect { println(it) }
    }
}