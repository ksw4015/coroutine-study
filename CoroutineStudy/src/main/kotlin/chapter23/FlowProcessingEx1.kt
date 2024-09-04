package org.example.chapter23

import kotlinx.coroutines.flow.*

/**
 * Flow 처리 함수
 * 일반적으로 컬렉션 함수들과 비슷
 */

suspend fun main() {
    flowOf(1,2,3)
        .map { it * it }
        .collect { println(it) }

    (1..10).asFlow()
        .filter { it <= 5 }
        .filter { it % 2 == 0 }
        .collect { println(it) }

    ('A'..'Z').asFlow()
        .take(5)
        .collect { print(it) }
    println()

    ('A'..'Z').asFlow()
        .drop(20)
        .collect { print(it) }
}