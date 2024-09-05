package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * shareIn
 *
 * 하나의 플로우를 여러개의 플로우로 만들고 싶을때
 *
 * Flow를 SharedFlow로 바꾸기위한 가장 쉬운방법으로 shareIn 함수가 있다.
 *
 *
 *
 */

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A","B","C")
        .onEach { delay(1000) }

    // shareId 함수는 sharedFlow를 만들고 Flow의 원소를 내보낸다.
    val sharedFlow: SharedFlow<String> = flow.shareIn(
        scope = this,
        // 값을 언제부터 감지할지 결정하는 인자
        // SharingStarted.Eagerly: 즉시 값을 감지하기 시작. replay 값에 제한이 있으며 감지를 시작하기 전에 나온 값은 유실될 수 있다.
        started = SharingStarted.Eagerly
        // replay = 0 (default)
    )

    delay(500)
    launch {
        sharedFlow.collect {
            println("#1 $it")
        }
    }

    delay(1000)
    launch {
        sharedFlow.collect {
            println("#2 $it")
        }
    }

    delay(1000)
    launch {
        sharedFlow.collect {
            println("#3 $it")
        }
    }

    // 1초후
    // #1 A
    // 2초후
    // #1 B, #2 B
    // 3초후
    // #1 C, #2 C, #3 C
}