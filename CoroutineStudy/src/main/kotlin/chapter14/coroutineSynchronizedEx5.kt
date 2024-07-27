package org.example.chapter14

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

/**
 * 4. 뮤텍스
 *  - 스레드가 아닌 코루틴을 중단
 *  - 전체 함수를 뮤텍스로 래핑하는 건 지양
 *  - 뮤텍스를 사용하기로 했다면 lock 를 두 번 걸지 않고, 중단 함수를 실행하지 않도록 해야한다.
 */


fun main() = runBlocking {
    massiveRun {
        // synchronized 와 비슷하지만, 스레드가 아닌 코루틴을 중단하기 때문에 훨씬 가볍다
        mutex.withLock {
            counter++
            // 코루틴은 lock 을 두 번 통과할 수 없다
//            mutex.withLock {
//               // 이렇게 쓰면 영원히 실행된다
//            }
        }
    }
    println(counter)

    // 코루틴이 중단되었을 때 뮤텍스를 풀 수 없다
    val timeMillis = measureTimeMillis {
        mutex.withLock {
            // 잠겨서 5초 걸림
            repeat(5) {
                delay(1000)
            }
        }
    }
    println(timeMillis) // 5036~
}

private var counter = 0
private val mutex = Mutex()