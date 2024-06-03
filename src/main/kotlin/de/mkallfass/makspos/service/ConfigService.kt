package de.mkallfass.makspos.service

import de.mkallfass.makspos.domain.Config
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.json.Json
import java.io.StringReader
import java.util.*

@ApplicationScoped
class ConfigService {
    private val LABEL_PREFIX = "label."
    private val CURRENCY_KEY = "currency"
    private val PAYMENT_PRESETS_KEY = "paymentPresets"

    @Inject
    lateinit var configRepository: ConfigRepository

    fun getConfig(locale: Locale): Config {
        Log.info("Get Config for locale: $locale")
        val bundle = configRepository.getConfigBundle(locale)

        val currency = bundle.getString(CURRENCY_KEY)
        val paymentPresets = convertJsonToNumberList(bundle.getString(PAYMENT_PRESETS_KEY))
        val labels = LinkedHashMap<String, String>()
        for (key in bundle.keys) {
            if (key.startsWith(LABEL_PREFIX)) {
                labels[key.removePrefix(LABEL_PREFIX)] = bundle.getString(key)
            }
        }
        return Config(currency = currency, paymentPresets = paymentPresets, labels = labels)
    }

    fun getConfig(language: String?): Config {
        Log.info("Get Config for language: $language")
        var locale = Locale.getDefault()
        if (language != null) {
            locale = Locale.forLanguageTag(Locale.LanguageRange.parse(language)[0].toString())
        }
        return getConfig(locale)
    }

    private fun convertJsonToNumberList(input: String): List<Number> {
        val reader = Json.createReader(StringReader(input))
        val array = reader.readArray()
        val list = mutableListOf<Number>()
        for (element in array) {
            list.add(element.toString().toDouble())
        }
        return list
    }
}