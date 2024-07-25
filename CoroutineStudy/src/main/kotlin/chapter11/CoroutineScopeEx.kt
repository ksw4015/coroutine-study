package org.example.chapter11

import kotlinx.coroutines.*

/**
 * suspend 함수를 동시에 실행해야 하는 경우
 * 1. GlobalScope
 *  - EmptyCoroutineContext를 가진 스코프
 *  - 부모 코루틴과 아무 관계가 없으므로, 여러가지 문제가 발생할 수 있다.
 *  - 작업이 끝날 때까지 자원낭비 -> 메모리 누수
 *
 * 2. scope를 인자로 넘기는 방법
 *  - 스코프가 함수로 전달되면 예상하지 못한 부작용 발생
 *  - async등에서 예외가 발생하면 모든 scope가 닫힌다 (Job)
 *
 * 3. coroutineScope 함수
 *  - coroutineScope 함수는 새로운 코루틴을 생성하지만 호출한 부모 코루틴을 중단한다.
 *  - 따라서, 동시에 작업을 시작할 수 없음
 */

fun main() {
    runBlocking {
        val a = coroutineScope {
            delay(1000)
            10
        }
        val b = coroutineScope {
            delay(1000)
            20
        }
        // 2초 후 출력
        println(a)
        println(b)

        val job = launch(CoroutineName("Parent")) {
            // 부모 코루틴이 취소되면 자식 코루틴도 모두 취소됨
            longTask() //  [Parent] Finished task1
        }
        delay(1500)
        job.cancelAndJoin()
    }
}

suspend fun longTask() = coroutineScope {
    launch {
        delay(1000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task1")
    }
    launch {
        delay(2000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task2")
    }
}