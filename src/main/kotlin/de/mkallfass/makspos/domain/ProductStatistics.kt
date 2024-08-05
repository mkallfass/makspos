package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents statistics about a product")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductStatistics(
    override var id: String,
    override var name: String? = null,
    override var description: String? = null,

    @Schema(name = "orderedCount", description = "The total count the product was ordered")
    @field:JsonProperty("orderedCount")
    var orderedCount: Double,

    @Schema(name = "revenue", description = "The revenue made with the product")
    @field:JsonProperty("revenue")
    var revenue: Double,
) : BaseProduct(id = id, name = name, description = description)