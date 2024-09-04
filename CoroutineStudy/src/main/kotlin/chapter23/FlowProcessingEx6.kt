package org.example.chapter23

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * retry: 플로우에서 예외 발생시 각 단계는 종료되지만 이전 참조를 갖고 있기때문에
 * 플로우를 다시 시작할 수 있다.
 */

// retryWhen 함수의 간단한 직접 구현
fun <T> Flow<T>.retryWhen(
    // 예외를 무시하고 이전 단계 플로우를 실행할 지,
    // 플로우를 종료할 지 결정하는 조건자
    predicate: suspend FlowCollector<T>.(
        cause: Throwable,
        attempt: Long
    ) -> Boolean
): Flow<T> = flow {
    var attempt = 0L
    do {
        val shallRetry = try {
            collect { emit(it) }
            false
        } catch (e: Throwable) {
            predicate(e, attempt++)
                .also { if(!it) throw e }
        }
    } while (shallRetry)
}

// 내부적으로 retryWhen을 사용하는 retry함수 직접 구현
fun <T> Flow<T>.retry(
    retries: Long,
    predicate: suspend FlowCollector<T>.(cause: Throwable) -> Boolean = { true }
): Flow<T> {
    require(retries > 0) {
        "Expected positive amount of retries, but had $retries"
    }
    return retryWhen { cause, attempt ->
        attempt < retries && predicate(cause)
    }
}

// retre 함수의 사용
suspend fun main() {
    flow {
        emit(1)
        emit(2)
        error("E")  // 3번의 retry 후 한번 더 실행 -> 예외
        emit(3)
    }.retry(3) {
        // 보통 Log를 남기고 새로운 연결을 시도할 때 간격을 둔다.
        delay(1000)
        print(it.message)
        println()
        true
    }.collect {
        print(it)
    }
}
