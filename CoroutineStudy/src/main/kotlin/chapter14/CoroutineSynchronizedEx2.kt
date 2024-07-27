package org.example.chapter14

import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

/**
 * 2. 원자성
 *  - 자바에서 제공하는 스레드 안전을 보장하는 원자값 사용
 */

private var counter = AtomicInteger()

fun main() = runBlocking {
    massiveRun {
        counter.incrementAndGet()
    }
    println(counter.get()) // 1000000

    // 하나의 연산에서 원자성을 갖고 있다해서 전체 연산에서 원자성이 보장되지는 않는다.
    counter.set(0)
    massiveRun {
        counter.set(counter.get() + 1)
    }
    println(counter.get())
}