package org.example.chapter3

import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 예외로 재개하기
 */

suspend fun main() {
    try {
        suspendCoroutine<Unit> { continuation ->
            continuation.resumeWithException(MyException())
        }
    } catch (e: MyException) {
        print(e.message)
    }
}

class MyException : Throwable("Just an exception")