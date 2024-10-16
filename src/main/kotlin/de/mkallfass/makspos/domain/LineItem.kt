package de.mkallfass.makspos.domain

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
    override var id: String,
    override var name: String? = null,
    override var description: String? = null,
    override var price: Double? = null,

    @Schema(name = "quantity", required = true, description = "The quantity of the ordered product")
    @Valid
    @NotNull
    @NotBlank(message = "quantity may not be blank")
    @field:JsonProperty("quantity")
    var quantity: Double,

    @Schema(name = "total", description = "The total price of the ordered product")
    @field:JsonProperty("total")
    var total: Double? = null
) : Product(id = id, name = name, description = description, price = price)