package org.example.chapter24

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * StateFlow는 데이터가 덮어 씌워지기 때문에
 * 모든 이벤트를 받기 위해선 SharedFlow를 사용해야 한다. (차이점)
 */

suspend fun main(): Unit = coroutineScope {
    val state = MutableStateFlow('X')

    launch {
        for(c in 'A'..'E') {
            delay(300)
            state.value = c
        }
    }

    state.collect {
        delay(1000)
        println(it)
    }

}