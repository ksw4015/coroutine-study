package org.example.chapter21

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * ChannelFlow
 */
data class User(val name: String)

interface UserApi {
    suspend fun takePage(pageNumber: Int): List<User>
}

class FakeUserApi : UserApi {
    private val users = List(20) { User("User$it") }
    private val pageSize: Int = 3

    override suspend fun takePage(pageNumber: Int): List<User> {
        delay(1000)
        return users
            .drop(pageSize * pageNumber)
            .take(pageSize)
    }
}

fun allUsersFlow(api: UserApi): Flow<User> = flow {
    var page = 0
    do {
        println("Fetching page $page")
        val users = api.takePage(page++)
        emitAll(users.asFlow())
    } while (!users.isNullOrEmpty())
}

fun allUserChannelFlow(api: UserApi): Flow<User> = channelFlow {
    var page = 0
    do {
        println("Fetching page $page")
        val users = api.takePage(page++)
        users?.forEach {
            send(it)
        }
    } while (!users.isNullOrEmpty())
}

suspend fun main() {
    val api = FakeUserApi()
    val users = allUsersFlow(api)
    val user = users
        .first {
            println("Checking $it")
            delay(1000)
            it.name == "User3"
        }
    println(user)

    val api2 = FakeUserApi()
    val channelUsers = allUserChannelFlow(api2)
    val user2 = channelUsers
        .first {
            println("Checking $it")
            delay(1000)
            it.name == "User3"
        }
    println(user2)
}