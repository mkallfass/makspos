package de.mkallfass.tools.minipos.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import de.mkallfass.tools.minipos.domain.Product

data class ProductsResponse(
    @field:JsonProperty("products")
    val products: List<Product>
)