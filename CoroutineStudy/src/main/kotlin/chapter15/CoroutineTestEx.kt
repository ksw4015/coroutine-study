package org.example.chapter15

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import kotlin.system.measureTimeMillis

/**
 * 코루틴 테스트
 * suspend 함수 테스트에 있어서 대부분의 경우 runBlocking과 어설트 지원 도구만 있으면 충분하다.
 *
 * 1. 시간 의존성 테스트
 */

/*
    -> test/kotlin/chapter15/CoroutineTestExTest.kt
 */
class CoroutineTestEx {
    val repo: UserDataRepository = FakeDelayedUserDataRepository()
    suspend fun produceCurrentUserSeq() {
    }

    suspend fun produceCurrentUserSym() {
    }
}

interface UserDataRepository {
    suspend fun getProfile() : Profile

    suspend fun getFriends() : List<Friend>
}

class FakeDelayedUserDataRepository : UserDataRepository {
    override suspend fun getProfile(): Profile {
        delay(1000)
        return Profile("")
    }

    override suspend fun getFriends(): List<Friend> {
        delay(1000)
        return emptyList()
    }
}

class ProduceUserUseCase(
    private val repo: UserDataRepository
) {
    suspend fun produceCurrentUserSeq() {
        val profile = repo.getProfile()
        val friends = repo.getFriends()
    }

    suspend fun produceCurrentUserSym() = coroutineScope {
        val profile = async { repo.getProfile() }
        val friends = async { repo.getFriends() }
        profile.await()
        friends.await()
    }
}

data class Profile(
    val name: String
)

data class Friend(
    val name: String
)