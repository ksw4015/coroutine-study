package org.example.chapter14

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * 5. 세마포어
 *  - 뮤텍스와 달리 여러 접근을 허용한다.
 */

suspend fun main() = coroutineScope {
    // 동시 요청을 2개로 제한
    val semaphore = Semaphore(2)

    repeat(5) {
        launch {
            // 2개씩 동시 실행
            // 동시성 문제를 해결해 주지는 않는다.
            semaphore.withPermit {
                delay(1000)
                print(it)  // 01 23 4, 10 32 4
            }
        }
    }
}

