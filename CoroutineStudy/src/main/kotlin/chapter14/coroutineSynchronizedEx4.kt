package org.example.chapter14

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

/**
 * 4. 뮤텍스
 *
 */

suspend fun main() = coroutineScope {
    repeat(5) {
        launch {
            delayAndPrint()
        }
    }
}

private val mutex = Mutex()

/*
    lock과 unlock을 직접 사용하는 방식은 위험,
    예외가 발생할 경우 unlock이 호출되지 않으므로, 데드락에 빠진다
 */
suspend fun delayAndPrint() {
    mutex.lock()
    delay(1000)
    println("Done")
    mutex.unlock()
}