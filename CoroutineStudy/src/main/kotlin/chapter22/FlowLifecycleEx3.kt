package org.example.chapter22

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * launchIn
 *
 * collect를 새로운 코루틴에서 실행할 수 있도록 해주는 확장함수
 */

suspend fun main(): Unit = coroutineScope {
    flowOf("User1", "User2")
        .onStart { println("Users:") }
        .onEach { println(it) }
        .launchIn(this)
}