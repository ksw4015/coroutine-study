package org.example.chapter10

import kotlinx.coroutines.*

/**
 * 2. 예외 발생 시 Coroutine의 종료 멈추기
 *  - SupervisorJob을 사용하면 자식에서 발생한 모든 예외를 무시할 수 있다.
 */

fun main() {
    // SupervisorJob을 사용하는 방법 - 1
    runBlocking {
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            delay(1000)
            throw Error("Some Error")
        }
        scope.launch {
            delay(2000)
            println("Will be printed")
        }
        delay(3000)
        println("Done 1")
    }

    // SupervisorJob을 사용하는 방법 - 2
    runBlocking {
        val job = SupervisorJob()
        launch(job) {
            delay(1000)
            throw Error("Some Error")
        }
        launch(job) {
            delay(2000)
            println("Will be printed")
        }
        delay(3000)
        println("Done 2")
    }

    // 잘못된 SupervisorJob 사용 예시
    runBlocking {
        // 부모 Coroutine이 없는 SupervisorJob은 일반 Job과 동일하다
        launch(SupervisorJob()) {
            launch {
                delay(1000)
                throw Error("Some Error")  // 예외 발생시 아래 Coroutine 빌더도 실행 X
            }
            launch {
                delay(2000)
                println("Will not be printed")
            }
        }
        delay(3000)
        println("Done 3")
    }
}

