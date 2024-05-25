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

    @Inject
    lateinit var productService: ProductService

    fun create(order: Order): String? {
        Log.info("Order received: ${order}")
        processOrder(order)
        orderRepository.add(order)
        Log.info("Order processed: ${order}")
        return order.id
    }

    fun getAll(): List<Order> {
        return orderRepository.getAll()
    }

    private fun processOrder(order: Order) {
        order.id = UUID.randomUUID().toString()
        order.date = ZonedDateTime.now()

        var orderTotal = 0.0
        for (i in order.lineItems) {
            val p = productService.getProductById(i.id)
            if (p != null) {
                i.name = p.name
                i.description = p.description
                i.price = p.price
            }
            else {
                throw IllegalArgumentException("LineItem id ${i.id} is not a valid product id")
            }
            val lineItemTotal = i.quantity * i.price!!
            i.total = lineItemTotal
            orderTotal += lineItemTotal
        }
        order.total = orderTotal
    }
}