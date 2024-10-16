package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.eclipse.microprofile.openapi.annotations.media.Schema

open class BaseProduct(
    @Schema(name = "id", required = true, description = "The id of the product")
    @Valid
    @NotNull
    @field:JsonProperty("id")
    @NotBlank(message = "id may not be blank")
    open var id: String,

    @Schema(name = "name", description = "The name of the product")
    @field:JsonProperty("name")
    open var name: String? = null,

    @Schema(name = "description", description = "The description of the product")
    @field:JsonProperty("description")
    open var description: String? = null
)