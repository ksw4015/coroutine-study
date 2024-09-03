package org.example.chapter22

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 플로우 생명주기 함수
 *
 */

suspend fun main() {
    // onEach: flow 값을 하나씩 받음.
    // onEach에서 delay를 넣게되면 값이 흐를때마다 지연된다.
    flowOf(1,2,3,4,5)
        .onEach {
            delay(200)
            print(it)
        }
        .collect {  }

    // onStart: 최종연산(Ex. collect)이 호출되면서 flow가 시작될 때 호출되는 리스너
    flowOf(1,2)
        .onStart { emit(0) }  // 원소를 내보낼 수 있다.
        .collect { println(it) }

    // onCompletion: flow가 완료되었을때 호출되는 리스너
    // flow가 마지막 값을 내보냈거나, 예외 or 코루틴이 취소 되었을때 호출
    flowOf(1,2)
        .onEach { delay(1000) }
        .onCompletion { println("Complete!") }
        .collect {
            println(it)
        }

    // onEmpty: 값을 내보내기 전에 완료되면 호출
    // onCatch: 플로우를 만들거나 처리도중 예외가 발생했을때 잡고 관리하기위한 리스너
    flow
        .onEach {
            println("Got $it")
        }
        .catch { println("Caught $it") }
        .collect {
            println("Collected $it")
        }

}

class MyError : Throwable("My error!")

val flow = flow {
    emit(1)
    emit(2)
    throw MyError()
}