package org.example.chapter24

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * SharedFlow
 *
 * replay 인자 설정 시 마지막으로 전송한 값들이 인자로 넘긴 숫자 만큼 저장됨 (캐싱?)
 */

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    // 마지막으로 전송되는 2개의 값 저장
    val mutableSharedFlow = MutableSharedFlow<String>(replay = 2)
    mutableSharedFlow.emit("Message1")
    mutableSharedFlow.emit("Message2")
    mutableSharedFlow.emit("Message3")
    // replaCache는 프로퍼티
    println(mutableSharedFlow.replayCache)
    launch {
        mutableSharedFlow.collect {
            println("#1 received $it")  // delay로 중단시키지 않고 emit을 먼저해도 캐싱되어있는 값은 출력된다.
        }
    }
    delay(1000)
    // 캐싱된 값 삭제
    mutableSharedFlow.resetReplayCache()
    println(mutableSharedFlow.replayCache)
}
