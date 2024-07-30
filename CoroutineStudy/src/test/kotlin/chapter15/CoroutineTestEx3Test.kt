package chapter15

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.example.chapter15.FakeDelayedUserDataRepository
import org.example.chapter15.ProduceUserUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * kotlinx-coroutines-test 에서 가장 흔하게 사용되는
 * runTest 함수
 */
class CoroutineTestEx3Test {
    // 코루틴을 시작하고 즉시 유휴상태까지 시간을 흐르게한다
    @Test
    fun test1() = runTest {
        assertEquals(0, currentTime) // TestScope 타입이라 currentTime 사용가능
        delay(1000)
        assertEquals(1000, currentTime)
    }

    @Test
    fun test2() = runTest {
        assertEquals(0, currentTime)
        coroutineScope {
            launch { delay(1000) }
            launch { delay(1500) }
            launch { delay(2000) }
        }
        assertEquals(2000, currentTime)
    }

    @Test
    fun `Should produce user sequentially`() = runTest {
        val userDataRepository = FakeDelayedUserDataRepository()
        val useCase = ProduceUserUseCase(userDataRepository)

        useCase.produceCurrentUserSeq()

        assertEquals(2000, currentTime)
    }

    @Test
    fun `Should produce user simultaneously`() = runTest {
        val userDataRepository = FakeDelayedUserDataRepository()
        val useCase = ProduceUserUseCase(userDataRepository)

        useCase.produceCurrentUserSym()

        assertEquals(1000, currentTime)
    }
}