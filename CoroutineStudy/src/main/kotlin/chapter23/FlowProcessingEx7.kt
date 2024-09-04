package org.example.chapter23

import kotlinx.coroutines.flow.*

/**
 * 중복 제거 함수
 */
private val NOT_SET = Any()

// distinctUntilChanged를 간단하게 구현한 함수
// 반복되는 원소가 동일하다고 생각되면 제거한다.
fun <T> Flow<T>.distinctUntilChanged(): Flow<T> = flow {
    var previous: Any? = NOT_SET
    collect {
        if (previous == NOT_SET || previous != it) {
            emit(it)
            previous = it
        }
    }
}

suspend fun main() {
    flowOf(1,2,2,3,2,1,1,4,5,1,2)
        .distinctUntilChanged()
        .collect {
            println(it) // 바로 이전 원소와 같으면 제거
        }

    // distinctUntilChangedBy: 원소가 동일한지 판단하기 위한 키 선택자를 인자로 받는 함수
    val users = flowOf(
        User(1, "Alex"),
        User(1, "Bob"),
        User(2, "Bob"),
        User(2, "Celine"),
    )

    println(users.distinctUntilChangedBy { it.id }.toList()) // 1 Alex, 2 Bob
    println(users.distinctUntilChangedBy { it.name }.toList()) // 1 Alex, 2 Bob, 2 Celine

}

data class User(val id: Int, val name: String) {
    override fun toString(): String = "[$id] $name"
}