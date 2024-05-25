package de.mkallfass.tools.minipos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents a line item of an order")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class LineItem(

    @Schema(name = "id", required = true, description = "The id of the ordered product")
    @Valid
    @NotNull
    @field:JsonProperty("id")
    @NotBlank(message = "id may not be blank")
    var id: String,

    @Schema(name = "name", description = "The name of the ordered product")
    @field:JsonProperty("name")
    var name: String? = null,

    @Schema(name = "description", description = "The description of the ordered product")
    @field:JsonProperty("description")
    var description: String? = null,

    @Schema(name = "price", description = "The price of the ordered product")
    @field:JsonProperty("price")
    var price: Double? = null,

    @Schema(name = "quantity", required = true, description = "The quantity of the ordered product")
    @Valid
    @NotNull
    @NotBlank(message = "quantity may not be blank")
    @field:JsonProperty("quantity")
    var quantity: Double,

    @Schema(name = "total", description = "The total price of the ordered product")
    @field:JsonProperty("total")
    var total: Double? = null
)