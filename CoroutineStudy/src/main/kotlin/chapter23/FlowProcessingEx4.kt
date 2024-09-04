package org.example.chapter23

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * fold: collect 처럼 최종연산이다
 * scan: 최종연산은 아님, 누적되는 과정의 모든값을 생성하는 중간연산
 */

suspend fun main() {
    val list = flowOf(1,2,3,4)
        .onEach { delay(1000) }

    // flow가 완료될 때까지 중단된다.
    val res = list.fold(0) { acc, i -> acc + i}
    println(res)  // 4초후 10

    // scan은 이전 단계에서 값을 받은 즉시 새로운 값을 만든다.
    val list2 = flowOf(1,2,3,4)
        .onEach { delay(500) }
        .scan(0) { acc, i -> acc + i}
        .collect { println(it) }

    val list3 = (1..10).asFlow()
        .onEach { delay(500) }
        .scan(Pair(0, 1)) { acc, _ ->
            Pair(acc.second, acc.first + acc.second)
        }
        .collect {
            println(it.first)
        }
}

