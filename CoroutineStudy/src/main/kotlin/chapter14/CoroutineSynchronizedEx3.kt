package org.example.chapter14

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.example.chapter11.User

/**
 * 3. 싱글스레드로 제한된 디스패처
 */

// 디스패처를 싱글스레드로 제한한 context
val dispatcher = Dispatchers.IO.limitedParallelism(1)

private var counter = 0

// 함수 실행이 느림
fun main() = runBlocking {
    massiveRun {
        withContext(dispatcher) {
            counter++
        }
    }
    println(counter)
}

class UserDownloader(
    private val api: NetworkService
) {
    private val users = mutableListOf<User>()
    private val dispatcher = Dispatchers.IO.limitedParallelism(1)

    suspend fun downloaded(): List<User> =
        withContext(dispatcher) {
            users.toList()
        }

    // 코스 그레인드 스레드 한정
    // withContext로 전체 함수를 래핑
    suspend fun fetchUser(id: String) = withContext(dispatcher) {
        val newUser = api.fetchUser(id)
        users += newUser
    }

    // 파인 그레인드 스레드 한정
    // withContext로 상태를 변경하는 구문만 래핑
    suspend fun fetchUser2(id: String) {
        val newUser = api.fetchUser(id)
        withContext(dispatcher) {
            users += newUser
        }
    }
}


interface NetworkService {
    suspend fun fetchUser(id: String) : List<User>
}


