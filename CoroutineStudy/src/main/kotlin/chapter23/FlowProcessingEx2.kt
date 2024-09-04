package org.example.chapter23

import kotlinx.coroutines.flow.flow

/**
 * 플로우에서 컬렉션 처리
 *
 */

suspend fun main() {
    flow map@{ // 1. collect로 람다식 시작
        flow flowOf@{ // 2. collect로 람다식 시작
            // 배열 탐색
            for (element in arrayOf('a', 'b')) {
                this@flowOf.emit(element) // 3. 값을 내보냄
            }
        }.collect { value ->
            this@map.emit(value.uppercase()) // 4. 3의 값을 받아서 처리 후 내보냄
        }
    }.collect {
        println(it) // 5. 4의 값
    }

    // 배열을 다 탐색할때까지 3, 4, 5 반복
}