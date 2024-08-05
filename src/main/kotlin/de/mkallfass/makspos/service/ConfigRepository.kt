package de.mkallfass.makspos.service

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.net.URLClassLoader
import java.nio.file.Paths
import java.util.*

@ApplicationScoped
class ConfigRepository {

    @ConfigProperty(name = "data.directory")
    lateinit var dataDirectory: String

    @ConfigProperty(name = "config.repository.bundle")
    lateinit var repository: String

    var repositoryBundles = LinkedHashMap<Locale, ResourceBundle>()

    fun getConfigBundle(locale: Locale): ResourceBundle {
        return getResourceBundle(locale)
    }

    private fun getResourceBundle(locale: Locale): ResourceBundle {
        Log.debug("Get resource bundle for locale: $locale")
        if (!repositoryBundles.containsKey(locale)) {
            val urls = arrayOf(Paths.get(dataDirectory).toUri().toURL())
            val classLoader = URLClassLoader(urls)
            val bundle = ResourceBundle.getBundle(repository, locale, classLoader)
            repositoryBundles[locale] = bundle
            Log.debug("Get resource bundle loaded for locale: $locale")
        }
        return repositoryBundles.getValue(locale)
    }
}