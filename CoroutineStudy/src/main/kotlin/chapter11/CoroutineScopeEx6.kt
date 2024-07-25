package org.example.chapter11

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

/**
 * withTimeoutOrNull
 * - withTimeout과 다르게 예외를 던지지않고, null값을 반환
 */

suspend fun fetchUser(): User {
    while (true) {
        yield()
    }
}

suspend fun getUserOrNull(): User? = withTimeoutOrNull(5000) {
    fetchUser()
}

suspend fun main(): Unit = coroutineScope {
    val user = getUserOrNull()
    println("User: $user") // 5초 뒤 null 출력
}