package org.example.chapter6

import kotlinx.coroutines.runBlocking
import org.example.chapter3.delay

/**
 * launch와 다르게 시작한 스레드를
 * 블로킹하는 runblocking 빌더
 *
 * 실제 사용 예시 2가지 경우
 * 1. 프로그램이 끝나는 걸 방지하기위해
 * 2. 유닛 테스트
 */
fun main() {
    runBlocking {
        delay(1000L)
        println("World!")
    }
    runBlocking {
        delay(1000L)
        println("World!")
    }
    runBlocking {
        delay(1000L)
        println("World!")
    }
    /*
        앞서 launch 빌더와 다르게 main() 함수에서 직접
        Thread.sleep(2000L)을 실행하지 않아도 코루틴이 실행된다.
     */
    println("Hello,")
}