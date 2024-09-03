package org.example.chapter21

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    // 1. flow를 만드는 가장 간단한 방법
    flowOf(1,2,3,4,5)
        .collect {
            // FlowCollector
            println(it)
        }

    // 2. 컨버터
    // asFlow를 이용해 Iterable, Iterator, Sequence를 Flow로
    listOf(1,2,3,4,5)
        .asFlow()
        .collect(::print)

    println()
    // 3. 함수를 Flow로
    // 중단 함수를 Flow로 변환 가능 함수의 결과가 Flow의 유일한 값이 된다.
    // ::를 이용한 함수 참조값도 변환 가능
    val f = suspend {
        delay(1000)
        "UserName"
    }

    f.asFlow()
        .collect { println(it) }
}