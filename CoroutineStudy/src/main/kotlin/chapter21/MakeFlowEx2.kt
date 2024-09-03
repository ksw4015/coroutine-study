package org.example.chapter21

import kotlinx.coroutines.flow.flow

/**
 * Flow 빌더
 */
suspend fun main() {
    // flow 빌더는 단지 Flow 객체를 만들뿐
    val flow = flow {
        emit("A")
        emit("B")
        emit("C")
    }
    // collect 호출시 block 함수(람다) 실행
    // (block 함수는 위의 flow 빌더의 인자)
    flow.collect {
        // block 함수 안에서 emit을 실행하면 이 람다식을 실행
        println(it)
    }
}