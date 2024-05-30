package de.mkallfass.makspos.service

import de.mkallfass.makspos.domain.Order
import de.mkallfass.makspos.domain.OrderStatistics
import de.mkallfass.makspos.domain.ProductStatistics
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
        Log.info("Order received: $order")
        processOrder(order)
        orderRepository.add(order)
        Log.info("Order processed: $order")
        return order.id
    }

    fun getAll(): List<Order> {
        return orderRepository.getAll()
    }

    fun getStatistics(): OrderStatistics {
        var overallRevenue = 0.0
        val productStatistics: ArrayList<ProductStatistics> = ArrayList()
        val orders = getAll()

        for (order in orders) {
            overallRevenue += order.total!!
            calculateProductStatistics(order, productStatistics)
        }

        return OrderStatistics(
            orderCount = orders.size,
            overallRevenue = overallRevenue,
            productStatistics = productStatistics.sortedByDescending { it.orderedCount })
    }

    private fun processOrder(order: Order) {
        order.id = UUID.randomUUID().toString()
        order.date = ZonedDateTime.now()

        var orderTotal = 0.0
        for (i in order.lineItems) {
            val p = productService.getProductById(i.id)
            if (p != null) {
                if (i.name != null && i.name != p.name) {
                    Log.warn("Name in lineitem ${i.name} differs from catalog ${p.name}")
                }
                i.name = p.name
                if (i.description != null && i.description != p.description) {
                    Log.warn("Description in lineitem ${i.description} differs from catalog ${p.description}")
                }
                i.description = p.description
                if (i.price != null && i.price != p.price) {
                    Log.warn("Price in lineitem ${i.price} differs from catalog ${p.price}")
                }
                i.price = p.price
            } else {
                throw IllegalArgumentException("Lineitem id ${i.id} is not a valid product id")
            }
            val lineItemTotal = i.quantity * i.price!!
            if (i.total != null && i.total != lineItemTotal) {
                Log.warn("Total in lineitem ${i.total} differs from calculated total ${lineItemTotal}")
            }
            i.total = lineItemTotal
            orderTotal += lineItemTotal
        }
        order.total = orderTotal
    }

    private fun calculateProductStatistics(order: Order, productStatistics: ArrayList<ProductStatistics>) {
        for (lineitem in order.lineItems) {
            var statistics = productStatistics.find { it.id == lineitem.id }
            if (statistics == null) {
                // Try to create statistics from product repository
                var product = productService.getProductById(lineitem.id)
                if (product == null) {
                    // Create statistics from line item
                    product = lineitem
                }
                statistics = ProductStatistics(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    orderedCount = 0.0,
                    revenue = 0.0
                )
                productStatistics.add(statistics)
            }
            statistics.orderedCount += lineitem.quantity
            statistics.revenue += lineitem.total!!
        }
    }
}