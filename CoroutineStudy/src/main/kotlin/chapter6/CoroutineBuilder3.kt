package org.example.chapter6

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.example.chapter3.delay

/**
 * async 빌더
 *
 * 1. launch와 비슷하나 값을 반환한다.
 * 2. Deferred<T> 객체를 리턴하고, Deferred에는 await라는 중단 메서드가있다.
 * 즉, 값이 필요할땐 async 필요하지 않을땐 launch
 */

fun main() {
    // suspend main 함수만으로는 Scope를 만들수 없어, runBlocking 사용
    runBlocking {
        val deferred = GlobalScope.async {
            delay(1000L)
            42 // 반환값
        }
        val result = deferred.await()
        println(result)
    }

    // runBlocking 함수의 block 파라미터 타입은
    // 리시버 타입이 CoroutineScope인 함수형 타입
    // 따라서, GlobalScope를 사용하지 않아도 launch와 async를 호출할 수 있다.
    runBlocking {
        // 아래의 3개 코루틴은 동시(병렬) 실행된다.
        val value1 = GlobalScope.async {
            delay(1000L)
            "Text 1"
        }
        val value2 = async {
            delay(4000L)
            "Text 2"
        }
        val value3 = this.async {
            delay(2000L)
            "Text 3"
        }
        // 1초후
        println(value1.await())
        // + 2초후
        println(value2.await())
        // value2를 기다리는 동안 value3의 작업은 이미 끝남
        println(value3.await())
    }
}