package chapter15

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * 백그라운드 스코프
 * - 가상 시간을 지원하지만, runTest가 scope가 종료될 때까지 기다리지 않음
 */
class CoroutineTestEx4Test {
    @Test
    fun `should increment counter`() = runTest {
        var i = 0
        backgroundScope.launch {
            while (true) {
                delay(1000)
                i++
            }
        }
        delay(1001)
        assertEquals(1, i)
        delay(1000)
        assertEquals(2, i)
    }
}