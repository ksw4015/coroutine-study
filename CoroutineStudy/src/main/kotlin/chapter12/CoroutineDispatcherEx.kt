package org.example.chapter12

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Coroutine Dispatcher
 *
 * 1. Dispatchers.Default
 *  - Cpu 집약적인 연산
 *  - 컴퓨터의 Cpu 와 동일한 수의 스레드 풀을 가지고있다.
 *
 * 2. Dispatchers.Main
 *  - 어플리케이션 프레임워크에서는 가장 중요한 메인(UI) 스레드 개념을 가지고있다.
 *  - 메인 스레드가 블로킹되면 어플리케이션이 멈추기때문에 조심히 다뤄야한다.
 *
 * 3. Dispatchers.IO
 *  - 시간이 오래걸리는 I/O 작업이나 블로킹 함수가 있는 라이브러리를 사용할 때 사용
 *  - Default Dispatcher를 블로킹하면 스레드 풀에 있는 모든 스레디를 블로킹하기 때문에,
 *    이런 상황에서는 Dispatchers.IO가 필요하다.
 */

suspend fun main() = coroutineScope {
    repeat(100) {
        launch(Dispatchers.Default) {
            val threadName = Thread.currentThread().name
            println("Running on thread: $threadName")
        }
    }
}