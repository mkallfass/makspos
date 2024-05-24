package de.mkallfass.tools.minipos.service

import de.mkallfass.tools.minipos.domain.Order
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderService {

    fun create(order: Order) {
        Log.info("Order received: ${order}")
    }
}