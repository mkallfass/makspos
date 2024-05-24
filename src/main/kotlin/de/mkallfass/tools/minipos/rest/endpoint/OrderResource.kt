package de.mkallfass.tools.minipos.rest.endpoint

import de.mkallfass.tools.minipos.domain.Order
import de.mkallfass.tools.minipos.rest.model.Error
import de.mkallfass.tools.minipos.rest.model.OrderResponse
import de.mkallfass.tools.minipos.service.OrderService
import io.quarkus.logging.Log
import jakarta.inject.Inject
import jakarta.validation.Valid
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

@Path("/api/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class OrderResource {

    @Inject
    lateinit var orderService: OrderService

    @Operation(summary = "Create a order")
    @APIResponses(
        value = [
            APIResponse(responseCode = "200", description = "The order was successfully processed"),
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
            orderService.create(order)
            Response.ok().entity(OrderResponse(id = "TODO")).build()
        } catch (e: Exception) {
            Log.error("Error while creating order ${order}", e)
            Response.serverError()
                .entity(Error(message = "Error while creating order ${order}: " + (e.message ?: e.toString()))).build()
        }
    }
}