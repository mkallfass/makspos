package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.ZonedDateTime

@Schema(description = "Represents an order")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Order(
    @Schema(name = "id", description = "The id of the order")
    @field:JsonProperty("id")
    var id: String? = null,

    @Schema(name = "date", description = "The order date/time")
    @field:JsonProperty("date")
    var date: ZonedDateTime? = null,

    @Schema(name = "lineitems", description = "The lineitems of the order")
    @Valid
    @NotEmpty
    @field:JsonProperty("lineitems")
    var lineItems: List<LineItem>,

    @Schema(name = "total", description = "The total price of the order")
    @field:JsonProperty("total")
    var total: Double? = null,
)