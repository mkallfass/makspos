package de.mkallfass.tools.minipos.rest.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderCreatedResponse(
    @field:JsonProperty("id")
    val id: String
)