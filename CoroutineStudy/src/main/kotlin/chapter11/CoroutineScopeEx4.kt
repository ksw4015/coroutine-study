package org.example.chapter11

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/**
 * supervisorScope
 * - coroutineScope와 비슷하나, Job을 SupervisorJob으로 오버라이딩한다.
 * - 서로 독립적인 작업을 시작하는 함수에서 주로 사용됨
 *
 * 주의!
 * withContext(SupervisorJob())은 여전히 기존에 가지고있던 Job을 사용하므로 의미가없다.
 */

fun main() {
    runBlocking {
        println("Before")
        supervisorScope {
            launch {
                delay(1000)
                throw Error("Some Error")
            }
            launch {
                delay(2000)
                println("Done")
            }
        }
        println("After")
    }
}