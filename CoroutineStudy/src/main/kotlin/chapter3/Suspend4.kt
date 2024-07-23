package org.example.chapter3

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * suspendCoroutine의 제네릭 타입을 지정하면
 * resume을 통해 값을 반환하면서 재개할 수 있다.
 */
suspend fun main() {
    val i: Int = suspendCoroutine { cont ->
        cont.resume(42)
    }
    println(i)
    val str: String = suspendCoroutine { cont ->
        cont.resume("Text")
    }
    println(str)
    val b: Boolean = suspendCoroutine { cont ->
        cont.resume(true)
    }
    println(b)
}