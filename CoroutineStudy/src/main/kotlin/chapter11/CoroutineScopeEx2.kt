package org.example.chapter11

import kotlinx.coroutines.*

/**
 * coroutineScope는 기존 중단 Context에서 벗어나 새로운 Scope를 만든다.
 */

fun main() {
    runBlocking(CoroutineName("Parent")) {
        val details = try {
            getUserDetails()
        } catch (e: ApiException) {
            null
        }

        val tweets = async { getTweets() }
        println("User $details")
        println("Tweets: ${tweets.await()}")
    }
}

data class User(
    val name: String,
    val tweets: List<Tweet>
)
data class Details(
    val name: String,
    val followers: Int
)
data class Tweet(val text: String)
class ApiException(
    val code: Int,
    message: String
) : Throwable(message)

fun getFollowersNumber(): Int =
    throw ApiException(500, "Internal Server Error")

suspend fun getUserName(): String {
    delay(500)
    return "seokwoo"
}

suspend fun getTweets(): List<Tweet> {
    return listOf(Tweet("Hello, World!"))
}

suspend fun getUserDetails(): Details = coroutineScope {
    val userName = async { getUserName() }
    val followersNumber = async { getFollowersNumber() }
    Details(userName.await(), followersNumber.await())
}

/**
 * 아래 두 함수는
 * 연속 호출과 병렬 호출의 차이일 뿐 결과는 같다.
 */
suspend fun produceCurrentUserSeq(): User {
    val name = getUserName()
    val tweets = getTweets()
    return User(name, tweets)
}

suspend fun produceCurrentUserSym(): User = coroutineScope {
    val name = async { getUserName() }
    val tweets = async { getTweets() }
    User(name.await(), tweets.await())
}