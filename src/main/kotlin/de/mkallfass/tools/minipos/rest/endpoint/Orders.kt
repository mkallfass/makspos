package de.mkallfass.tools.minipos.rest.endpoint

import de.mkallfass.tools.minipos.domain.Order
import de.mkallfass.tools.minipos.rest.model.Error
import de.mkallfass.tools.minipos.rest.model.OrderCreatedResponse
import de.mkallfass.tools.minipos.service.OrderService
import io.quarkus.logging.Log
import jakarta.inject.Inject
import jakarta.validation.ConstraintViolation
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import jakarta.validation.Validator
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses

@Path("/api/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class Orders {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var orderService: OrderService

    @Operation(summary = "Create an order")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "201",
                description = "The order was successfully processed",
                content = arrayOf(
                    Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = Schema(implementation = OrderCreatedResponse::class)
                    )
                )
            ),
            APIResponse(
                responseCode = "500",
                description = "Unexpected error",
                content = arrayOf(
                    Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = Schema(implementation = Error::class)
                    )
                )
            )
        ]
    )
    @POST
    fun order(@Valid order: Order): Response {
        return try {
            validateOrder(order)
            val orderId = orderService.create(order)
            Response.ok().status(Response.Status.CREATED).entity(OrderCreatedResponse(id = orderId!!)).build()
        } catch (e: Exception) {
            Log.error("Error while creating order ${order}", e)
            Response.serverError()
                .entity(Error(message = "Error while creating order ${order}: " + (e.message ?: e.toString()))).build()
        }
    }

    // Todo
    private fun validateOrder(order: Order) {
        val violations: Set<ConstraintViolation<Order>> = validator.validate<Order>(order)
        if (violations.isNotEmpty()) {
            throw ValidationException(violations.toString())
        }
    }

}