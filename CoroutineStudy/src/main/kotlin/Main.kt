package org.example

import java.math.BigInteger

fun main() {
    // sequence함수의 매개변수는 수신객체 지정 람다
    val seq: Sequence<Int> = sequence {
        println("첫번째 값 생성!")
        yield(1)
        println("두번째 값 생성!")
        yield(2)
        println("세번째 값 생성!")
        yield(3)
        println("시퀀스 종료!")
    }
    for (num in seq) {
        println("생성된 값은? $num")
    }

    // 시퀀스를 이용한 피보나치 표현
    val fibonacci: Sequence<BigInteger> = sequence {
        var first = BigInteger.valueOf(0)
        var second = BigInteger.valueOf(1)
        while (true) {
            yield(first)
            val temp = first
            first += second
            second = temp
        }
    }
    println(fibonacci.take(10).toList())
}