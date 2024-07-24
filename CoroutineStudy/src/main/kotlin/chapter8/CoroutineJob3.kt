package org.example.chapter8

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job1 = launch {
            delay(1000)
            println("Test1")
        }
        val job2 = launch {
            delay(2000)
            println("Test2")
        }

        // join 메서드로 job이 완료될때까지 기다리는 중단 함수
//        job1.join()
//        job2.join()
        // join 으로 기다리지 않으면 main함수 실행 즉시 아래 구문이 출력된다
//        println("All tests are done!")

        val children  = coroutineContext.job.children // 모든 자식을 참조할 수 있는 children 프로퍼티
        val childrenNum = children.count()
        println("Number of children: $childrenNum")
        children.forEach {
            it.join()
        }
        println("All tests are done!")
    }
}