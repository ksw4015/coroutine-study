package org.example.chapter23

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * 플로우 함치기
 *
 * merge: 두 개의 flow를 합침. 다른 플로우를 기다리지 않기 때문에 원소들이 섞임
 * zip: 두 플로우로 부터 쌍을 만듦. 쌍을 이루기위해 먼저 처리된 원소가 쌍이될 원소를 기다림
 * combine: 두 플로우로 부터 쌍을 만든다는 것은 zip과 같으나,
 *  서로 모든 원소를 내보내고 두 데이터 사이 소스의 변화를 능동적으로 체크해서 쌍을 만든다.
 *
 */

suspend fun main() {
    val ints: Flow<Int> = flowOf(1,2,3)
    val doubles: Flow<Double> = flowOf(0.1, 0.2, 0.3)

    val together: Flow<Number> = merge(ints, doubles)
    println(together.toList())  // 순서는 보장되지 않음, 따라서 여러 개의 이벤트들을 똑같은 방법으로 처리할 때 사용

    // zip에선 쌍을 이루는 원소를 기다린다.
    // 따라서 delay 1000 적용
    val flow1 = flowOf("A", "B", "C")
        .onEach { delay(400) }
    val flow2 = flowOf(1,2,3,4)
        .onEach { delay(1000) }

    flow1.zip(flow2) { f1, f2 ->
        "${f1}_$f2"
    }.collect {
        println(it)
    } // 쌍을 이루지 못하는 원소는 유실

    // combine
    val flow3 = flowOf("A","B","C")
        .onEach { delay(400) }
    val flow4 = flowOf(1,2,3,4)
        .onEach { delay(1000) }
    flow3.combine(flow4) { f1, f2 -> "${f1}_$f2" }
        .collect { println(it) }
}