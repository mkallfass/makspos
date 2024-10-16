package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents statistics about the placed orders")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrderStatistics(
    @Schema(name = "orderCount", description = "The total count of order")
    @field:JsonProperty("orderCount")
    var orderCount: Int,

    @Schema(name = "overallRevenue", description = "The overall revenue")
    @field:JsonProperty("overallRevenue")
    var overallRevenue: Double,

    @Schema(name = "productStatistics", description = "The statistics per product")
    @field:JsonProperty("productStatistics")
    var productStatistics: List<ProductStatistics>,
)