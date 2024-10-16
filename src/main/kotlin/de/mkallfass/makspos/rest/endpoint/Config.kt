package de.mkallfass.makspos.rest.endpoint

import de.mkallfass.makspos.rest.model.Error
import de.mkallfass.makspos.service.ConfigService
import io.quarkus.logging.Log
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.jboss.resteasy.reactive.RestHeader

@Path("/api/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class Config {

    @Inject
    lateinit var configService: ConfigService

    @Operation(summary = "Get the config")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "The config",
                content = arrayOf(
                    Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = Schema(implementation = Config::class)
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
    fun getConfig(@RestHeader("Accept-Language") language: String?): Response {
        return try {
            val config = configService.getConfig(language)
            Response.ok().entity(config).build()
        } catch (e: Exception) {
            Log.error("Error while getting config", e)
            Response.serverError()
                .entity(Error(message = "Error while getting config: " + (e.message ?: e.toString()))).build()
        }
    }
}