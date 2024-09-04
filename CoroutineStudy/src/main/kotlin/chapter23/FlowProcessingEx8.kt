package org.example.chapter23

import kotlinx.coroutines.flow.*

/**
 * Flow의 최종연산
 *
 * collect, count, first, firstOrNull, fold, reduce
 *
 */

suspend fun main() {
    val flow = flowOf(1,2,3,4,5)
        .map { it * it }

    println(flow.first())
    println(flow.count())

    println(flow.reduce { acc, value -> acc * value})
    println(flow.fold(0) { acc, value -> acc + value})

    println(flow.sum())
}

// 최종연산은 위에 있는게 전부지만 직접구현할 수도 있다.
suspend fun Flow<Int>.sum(): Int {
    var sum = 0
    collect {
        sum += it
    }
    return sum
}