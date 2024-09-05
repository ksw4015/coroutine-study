package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 코틀린에서는 감지만하는 인터페이스와 변경하는 인터페이스를 구분하는것이 관행
 *
 * 감지 = SharedFlow, 변경 = FlowCollector
 *
 * MutableSharedFlow는 SharedFlow와 FlowCollector를 모두 상속한다
 *
 */

suspend fun main(): Unit = coroutineScope {
    val mutableShardFlow = MutableSharedFlow<String>()
    val sharedFlow: SharedFlow<String> = mutableShardFlow
    val collector: FlowCollector<String> = mutableShardFlow

    launch {
        mutableShardFlow.collect {
            println("#MutableSharedFlow received $it")
        }
    }

    launch {
        sharedFlow.collect {
            println("#SharedFlow received $it")
        }
    }

    delay(1000)
    mutableShardFlow.emit("Message1")
    collector.emit("Message2")
}