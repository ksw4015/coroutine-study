package org.example.chapter20

import kotlinx.coroutines.delay

suspend fun main() {
    val f: suspend () -> Unit = {
        print("A")
        delay(1000)
        print("B")
        delay(1000)
        print("C")
    }
    f()
    f()

    println()
    val f2: suspend ((String) -> Unit) -> Unit = { emit ->
        emit("A")
        emit("B")
        emit("C")
    }

    f2(::print)
    f2(::print)
    println()

    // f2처럼 람다식을 전달하는 대신 interface를 구현한 객체를 만드는 편이 낫다.
    val f3: suspend (FlowCollector<String>) -> Unit = {
        it.emit("A")
        it.emit("B")
        it.emit("C")
    }
    f3(::print)
    f3(::print)
    println()

    // f3에서 it.emit으로 호출하는 부분을 간단히 하기위해, 리시버로 만듦
    val f4: suspend FlowCollector<String>.() -> Unit = {
        emit("A")
        emit("B")
        emit("C")
    }
    f4(::print)
    f4(::print)
    println()

    val f5: Flow<String> = flow {
        emit("A")
        emit("B")
        emit("C")
    }

    f5.collect(::print)
    f5.collect(::print)
}

// SAM
fun interface FlowCollector<T> {
    suspend fun emit(value: T)
}

// 플로우 생성을 간단하게 만들기 위한 flow 빌더 정의
interface Flow<T> {
    suspend fun collect(collector: FlowCollector<T>)
}

fun <T> flow(
    builder: suspend FlowCollector<T>.() -> Unit
) = object : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        collector.builder()
    }
}