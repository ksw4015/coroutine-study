package org.example.chapter14

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * 코루틴 동시성 문제
 *
 * 1. 동기화 블로킹
 */
private var counter = 0

fun main() = runBlocking {
    massiveRun {
        counter++
    }
    println(counter) // 동기화가 되지 않아 1,000,000(1,000 * 1,000)이 아닌 훨씬 작은 숫자가 출력

    counter = 0
    val lock = Any()
    massiveRun {
        synchronized(lock) {
            counter++
        }
    }
    println("Counter = $counter") // 1000000
    // 위 방식의 문제점
    // 1. synchronized 블록 안에서 suspend 함수를 사용하지 못함
    // 2. synchronized 블록에서 코루틴이 자기 차례를 기다릴 때 스레드를 블로킹함
}

suspend fun massiveRun(action: suspend () -> Unit) {
    withContext(Dispatchers.Default) {
        repeat(1000) {
            launch {
                repeat(1000) {
                    action()
                }
            }
        }
    }
}