package de.mkallfass.tools.minipos.rest.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderResponse(
    @field:JsonProperty("id")
    val id: String
)