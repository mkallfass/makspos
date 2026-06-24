package de.mkallfass.makspos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Represents the config of makspos")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Config(
    @Schema(name = "currency", required = true, description = "The currency")
    @Valid
    @NotNull
    @NotBlank(message = "currency may not be blank")
    @field:JsonProperty("currency")
    var currency: String,

    @Schema(name = "paymentPresets", required = true, description = "The payment presets")
    @field:JsonProperty("paymentPresets")
    var paymentPresets: List<Number>,

    @Schema(name = "labels", required = true, description = "The labels for the ui")
    @field:JsonProperty("labels")
    var labels: Map<String, String>
)