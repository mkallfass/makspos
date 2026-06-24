package de.mkallfass.makspos.rest.endpoint

import de.mkallfass.makspos.domain.Product
import de.mkallfass.makspos.rest.model.Error
import de.mkallfass.makspos.service.ProductService
import io.quarkus.logging.Log
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses

@Path("/api/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class Products {

    @Inject
    lateinit var productService: ProductService

    @Operation(summary = "Get the list of products")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "The list of products",
                content = arrayOf(
                    Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = Schema(
                            type = SchemaType.ARRAY,
                            implementation = Product::class
                        )
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
    fun getProducts(): Response {
        return try {
            val products = productService.getProductList()
            Response.ok().entity(products).build()
        } catch (e: Exception) {
            Log.error("Error while getting products", e)
            Response.serverError()
                .entity(Error(message = "Error while getting products: " + (e.message ?: e.toString()))).build()
        }
    }
}