package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents a a product")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Product(
    override var id: String,
    override var name: String? = null,
    override var description: String? = null,

    @Schema(name = "price", description = "The price of the product")
    @field:JsonProperty("price")
    open var price: Double? = null,
) : BaseProduct(id = id, name = name, description = description)