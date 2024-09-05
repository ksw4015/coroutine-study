package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * shareIn
 *
 */

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A","B","C")
    val flow2 = flowOf("D")
        .onEach { delay(1000) }

    val sharedFlow: SharedFlow<String> = merge(flow, flow2).shareIn(
        scope = this,
        // SharingStarted.Lazily: 첫 번째 구독자가 나올때 감지하기 시작. 첫 번째 구독자는 모든값을 보장한다.
        started = SharingStarted.Lazily
    )

    delay(100)
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
}