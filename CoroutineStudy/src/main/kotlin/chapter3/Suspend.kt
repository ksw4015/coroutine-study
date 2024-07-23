package org.example.chapter3

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Chapter2
 *
 * - 코루틴의 중단과 재개
 */
suspend fun main() {
    println("Before")

    // 중단지점으로 코틀린 라이브러리에서 제공하는
    // suspendCoroutine 함수 사용
    suspendCoroutine<Unit> { continuation ->
        println("After는 실행되지 않으며, 함수는 실행된 상태로 중지되어 있다.")
        // 코루틴은 중단되었을때, Continuation 객체를 반환한다.
        // Continuation을 이용해 코루틴을 재개할 수 있다.
        continuation.resume(Unit)
        println("resume으로 재개 후 After가 출력된다.")
    }

    println("After")
}