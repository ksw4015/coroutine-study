package org.example.chapter10

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/**
 * 3. 예외 전파를 막는 또 다른 방법
 *  - supervisorScope로 래핑
 */

fun main() {
    runBlocking {
        // 예외가 발생해도 부모와의 연결을 유지한다.
        supervisorScope {
            launch {
                delay(1000)
                throw Error("Some Error")
            }
            launch {
                delay(2000)
                println("Will be printed")
            }
        }
        delay(1000)
        println("Done")
    }
}