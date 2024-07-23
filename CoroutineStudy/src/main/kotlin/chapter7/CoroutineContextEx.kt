package org.example.chapter7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * CoroutineContext는 컬렉션과 비슷하다.
 */
fun main() {
    val ctx: CoroutineContext = CoroutineName("A name")

    val coroutineName: CoroutineName? = ctx[CoroutineName]  // 컴패니언 객체를 Key로 원소를 찾는다.
    println(coroutineName?.name)
    val job: Job? = ctx[Job]
    println(job)
}