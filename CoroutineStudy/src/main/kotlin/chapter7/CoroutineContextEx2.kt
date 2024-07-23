package org.example.chapter7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * 컨텍스트 더하기
 * - 다른 키를 가진 두 원소를 더할 수 있다.
 */
fun main() {
    val ctx1: CoroutineContext = CoroutineName("Name1")
    println(ctx1[CoroutineName]?.name)
    println(ctx1[Job]?.isActive) // null

    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name) // null
    println(ctx2[Job]?.isActive)

    val ctx3: CoroutineContext = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name)  // Name1
    println(ctx3[Job]?.isActive)  // true

    // 같은 키를 가진 또 다른 원소가 더해지면 맵처럼 기존원소를 대체한다
    val ctx4: CoroutineContext = ctx3 + CoroutineName("Name2")
    println(ctx4[CoroutineName]?.name)  // Name2
    println(ctx4[Job]?.isActive)

    // minusKey 함수로 컨텍스트의 원소를 제거할 수 있다
    val ctx5 = ctx4.minusKey(CoroutineName)
    println(ctx5[CoroutineName]?.name)  // null
    println(ctx5[Job]?.isActive)
}