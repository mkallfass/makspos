package de.mkallfass.tools.minipos.service

import de.mkallfass.tools.minipos.domain.Order
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.ZonedDateTime
import java.util.*

@ApplicationScoped
class OrderService {

    @Inject
    lateinit var orderRepository: OrderRepository

    fun create(order: Order): String? {
        Log.info("Order received: ${order}")
        order.id = UUID.randomUUID().toString()
        order.date = ZonedDateTime.now()
        calculateOrder(order)
        orderRepository.add(order)
        Log.info("Order processed: ${order}")
        return order.id
    }

    fun getAll(): List<Order> {
        return orderRepository.getAll()
    }

    private fun calculateOrder(order: Order) {
        var orderTotal = 0.0
        for (i in order.lineItems) {
            i.price?.let { i.price = 0.0 }
            val lineItemTotal = i.quantity * i.price!!
            i.total = lineItemTotal
            orderTotal += lineItemTotal
        }
        order.total = orderTotal
    }
}