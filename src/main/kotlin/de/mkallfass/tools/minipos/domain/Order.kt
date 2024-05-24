package de.mkallfass.tools.minipos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.time.ZonedDateTime

@Schema(description = "Represents an order")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Order(
    @field:JsonProperty("id")
    var id: String? = null,

    @field:JsonProperty("date")
    var date: ZonedDateTime? = ZonedDateTime.now(),

    @field:JsonProperty("lineitems")
    var lineItems: List<LineItem>,

    @field:JsonProperty("total")
    var total: Double? = null,
)