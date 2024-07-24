package org.example.chapter10

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import org.example.chapter3.MyException

/**
 * 4. async Coroutine 빌더에서 예외가 발생했을 때, await 호출
 */

suspend fun main() {
    supervisorScope {
        val str1 = async<String> {
            delay(1000)
            throw MyException()
        }

        val str2 = async {
            delay(2000)
            "Text2"
        }

        try {
            println(str1.await()) // 예외 발생 catch로
        } catch (e: MyException) {
            println("catch: $e")
        }

        println(str2.await())
    }
}