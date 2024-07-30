package chapter15

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.example.chapter15.FakeDelayedUserDataRepository
import org.example.chapter15.UserDataRepository
import org.junit.jupiter.api.Test

import kotlin.system.measureTimeMillis

class CoroutineTestExTest {
    val repo: UserDataRepository = FakeDelayedUserDataRepository()

    @Test
    fun produceCurrentUserSeq() = runBlocking {
        val time = measureTimeMillis {
            val profile = repo.getProfile()
            val friends = repo.getFriends()
        }
        print(time) // 2034
    }

    @Test
    fun produceCurrentUserSym() = runBlocking {
        val time = measureTimeMillis {
            val profile = async { repo.getProfile() }
            val friends = async { repo.getFriends() }
            profile.await()
            friends.await()
        }
        print(time) // 1021
    }
}