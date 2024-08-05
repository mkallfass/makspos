package de.mkallfass.makspos.rest.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Error(
    @field:JsonProperty("message")
    val message: String
)
