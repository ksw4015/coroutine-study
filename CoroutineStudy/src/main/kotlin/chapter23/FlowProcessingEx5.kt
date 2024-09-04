package org.example.chapter23

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * flatMapConcat:
 */

fun flowFrom(ele: String) = flowOf(1,2,3)
    .onEach { delay(1000) }
    .map { "${it}_$ele" }

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() {
    flowOf("A", "B", "C")
        .flatMapConcat { flowFrom(it) } // 플로우를 하나씩 처리한다 따라서 A 3초 B 3초 C 3초
        .collect {
            println(it)
        }

    // flatMapMerge는 만들어진 플로우를 동시에 처리한다.
    // 1A,1B,1C -> 2A,2B,2C -> 3A,3B,3C
    flowOf("A","B","C")
        .flatMapMerge { flowFrom(it) }
        .collect {
            println(it)
        }

    // flatMapLatest: 새로운 플로우가 나타나면 이전에 처리하던 플로우는 잊어버린다.
    flowOf("A", "B", "C")
        .flatMapLatest { flowFrom(it) }
        .collect { println(it) }
    // 1C, 2C, 3C만 처리
}