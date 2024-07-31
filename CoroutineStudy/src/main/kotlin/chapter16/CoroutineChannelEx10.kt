package org.example.chapter16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class Order(
    val type: String,
    val customer: String
)

data class Coffee(
    val type: String
)

data class CoffeeResult(
    val coffee: Coffee,
    val customer: String,
    val baristaName: String
)

/**
 *
 */
suspend fun CoroutineScope.serveOrders(
    orders: ReceiveChannel<Order>,
    baristaName: String
): ReceiveChannel<CoffeeResult> = produce {
    for(order in orders) {
        val coffee = Coffee(order.type)
        send(
            CoffeeResult(
                coffee = coffee,
                customer = order.customer,
                baristaName = baristaName
            )
        )
    }
}

suspend fun main(): Unit = coroutineScope {
    val orderChannel = produce {
        send(Order(
            type = "Espresso",
            customer = "Kim"
        ))
        send(Order(
            type = "Lungo",
            customer = "Choi"
        ))
        send(Order(
            type = "Latte",
            customer = "Lee"
        ))
    }

    val coffeeResults = fanIn(
        listOf(
            serveOrders(orderChannel, "Alex"),
            serveOrders(orderChannel, "Bob"),
            serveOrders(orderChannel, "Celine")
        )
    )

    for(result in coffeeResults) {
        println(result)
    }
}

/*
    다수의 채널을 하나로 합쳐야 하는 경우 사용할 수 있는
    헬퍼 함수
 */
fun <T> CoroutineScope.fanIn(
    channels: List<ReceiveChannel<T>>
): ReceiveChannel<T> = produce {
    for(channel in channels) {
        launch {
            for(e in channel) {
                send(e)
            }
        }
    }
}

