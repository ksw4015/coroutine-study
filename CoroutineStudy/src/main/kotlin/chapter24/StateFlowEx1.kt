package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * StateFlow
 *
 * 1. replay 인자 값이 1인 SharedFlow 와 비슷하게 작동한다.
 * 2. value 프로퍼티로 접근 가능한 값 하나를 항상 가지고 있다. (중요)
 * 3. 초기값은 생성자를 통해 전달하고, value로 값을 얻어올 수도 설정할 수도 있다.
 */

suspend fun main() = coroutineScope {
    val state = MutableStateFlow("A")
    println(state.value)  // 초기값 A 출력

    launch {
        state.collect {
            println("Value changed to $it") // 종료되나?
        }
    }

    delay(1000)
    state.value = "B"

    delay(1000)

    launch {
        state.collect {
            println("and now it is $it")
        }
    }

    delay(1000)
    state.value = "C"
}