package org.example.chapter6

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 코루틴 빌더
 *
 * 1. 중단 함수를 시작하는 지점
 * 2. 일반 함수와 중단 함수를 연결
 * 3. 언어 차원에서의 코루틴이 아닌 코루틴 라이브러리에서 제공한다.
 * 4. launch, runBlocking, async 3가자의 필수 코루틴 빌더가 있다.
 */
fun main() {
    /*
        Launch 빌더
        - 코틀린에서 thread함수를 호출하여 새로운 스레드를 시작하는 것과 비슷
    */
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    Thread.sleep(2000L)  // 스레드를 잠들게 하지 않으면, 코루틴이 일할 기회가 없다.
}

