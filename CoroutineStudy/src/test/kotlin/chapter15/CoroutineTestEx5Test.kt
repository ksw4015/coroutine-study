package chapter15

import kotlinx.coroutines.*
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 취소와 컨텍스트 전달 테스트
 */
class CoroutineTestEx5Test {
    suspend fun <T, R> Iterable<T>.mapAsync(
        transformation: suspend (T) -> R
    ): List<R> = coroutineScope {
        this@mapAsync.map { async { transformation(it) } }
            .awaitAll()
    }

    @Test
    fun `should map async and keep elements order`() = runTest {
        val transform = listOf(
            suspend { delay(3000); "A" },
            suspend { delay(2000); "B" },
            suspend { delay(4000); "C" },
            suspend { delay(1000); "D" },
        )

        val res = transform.mapAsync { it() }
        assertEquals(listOf("A","B","C","D"), res)
        assertEquals(4000, currentTime)
    }

    // 중단 함수에서 컨텍스트확인
    @Test
    fun `should support context propagation`() = runTest {
        var ctx: CoroutineContext? = null
        val name1 = CoroutineName("Name 1")
        withContext(name1) {
            listOf("A").mapAsync {
                ctx = currentCoroutineContext()
                it
            }
            assertEquals(name1, ctx?.get(CoroutineName))
        }

        val name2 = CoroutineName("Name 2")
        withContext(name2) {
            listOf(1,2,3).mapAsync {
                ctx = currentCoroutineContext()
                it
            }
            assertEquals(name2, ctx?.get(CoroutineName))
        }
    }
}