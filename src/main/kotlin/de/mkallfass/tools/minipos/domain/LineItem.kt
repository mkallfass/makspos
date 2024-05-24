package de.mkallfass.tools.minipos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents a line item of an order")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class LineItem(
    @field:JsonProperty("id")
    @NotBlank(message = "id may not be blank")
    var id: String,

    @field:JsonProperty("name")
    var name: String? = null,

    @field:JsonProperty("description")
    var description: String? = null,

    @field:JsonProperty("price")
    @NotBlank(message = "price may not be blank")
    var price: Double,

    @field:JsonProperty("quantity")
    @NotBlank(message = "quantity may not be blank")
    var quantity: Double,

    @field:JsonProperty("total")
    var total: Double? = null
)