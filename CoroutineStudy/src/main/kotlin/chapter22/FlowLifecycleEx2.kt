package org.example.chapter22

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * flowOn
 */

fun userFlow(): Flow<String> = flow {
    repeat(2) {
        val ctx = currentCoroutineContext()
        val name = ctx[CoroutineName]?.name
        emit("User$it in $name")
    }
}

suspend fun present(place: String, message: String) {
    val ctx = coroutineContext
    val name = ctx[CoroutineName]?.name
    println("[$name] $message on $place")
}

fun messageFlow(): Flow<String> = flow {
    present("flow builder", "Message")
    emit("Message")
}

suspend fun main() {
    val users = messageFlow()
    // flowOn 함수로 컨텍스트 변경가능
    withContext(CoroutineName("Name1")) {
        users
            .flowOn(CoroutineName("Name3"))
            .onEach { present("onEach", it) }
            .flowOn(CoroutineName("Name2"))
            .collect { present("collect", it) }
    }
    // flowOn도 윗부분에 있는 함수에서만 작동
//    [Name3] Message on flow builder
//    [Name2] Message on onEach
//    [Name1] Message on collect
}