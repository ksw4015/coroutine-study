package chapter15

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.Test

class CoroutineTestEx2Test {
    @Test
    fun `test standard test dispatcher`() {
        val dispatcher = StandardTestDispatcher()
        CoroutineScope(dispatcher).launch {
            println("Some work 1")
            delay(1000)
            println("Some work 2")
            delay(1000)
            println("Coroutine Done")
        }

        println("[${dispatcher.scheduler.currentTime}] Before")
        dispatcher.scheduler.advanceUntilIdle() // scheduler를 작동시켜야 시간이 흐른다
        println("[${dispatcher.scheduler.currentTime}] After")
    }
}