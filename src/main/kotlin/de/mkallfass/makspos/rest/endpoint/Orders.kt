package de.mkallfass.makspos.rest.endpoint

import de.mkallfass.makspos.domain.Order
import de.mkallfass.makspos.domain.OrderStatistics
import de.mkallfass.makspos.rest.model.Error
import de.mkallfass.makspos.service.OrderService
import io.quarkus.logging.Log
import jakarta.inject.Inject
import jakarta.validation.ConstraintViolation
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import jakarta.validation.Validator
import jakarta.ws.rs.*
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
                        schema = Schema(implementation = Order::class)
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
            orderService.create(order)
            Response.ok().status(Response.Status.CREATED).entity(order).build()
        } catch (e: Exception) {
            Log.error("Error while creating order $order", e)
            Response.serverError()
                .entity(Error(message = "Error while creating order $order: " + (e.message ?: e.toString()))).build()
        }
    }

    @Operation(summary = "Get the order statistics")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "The order statistics were successfully calculated",
                content = arrayOf(
                    Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = Schema(implementation = OrderStatistics::class)
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
    @GET
    @Path("/statistics")
    fun getOrderStatistics(): Response {
        return try {
            val statistics = orderService.getStatistics()
            Response.ok().status(Response.Status.OK).entity(statistics).build()
        } catch (e: Exception) {
            Log.error("Error while calculating order statistics", e)
            Response.serverError()
                .entity(Error(message = "Error while calculating order statistics: " + (e.message ?: e.toString())))
                .build()
        }
    }

    // TODO
    private fun validateOrder(order: Order) {
        val violations: Set<ConstraintViolation<Order>> = validator.validate(order)
        if (violations.isNotEmpty()) {
            throw ValidationException(violations.toString())
        }
    }

}