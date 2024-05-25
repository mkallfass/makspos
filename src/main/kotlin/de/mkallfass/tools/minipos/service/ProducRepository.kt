package de.mkallfass.tools.minipos.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.mkallfass.tools.minipos.domain.Product
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@ApplicationScoped
class ProducRepository {

    val mapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

    @ConfigProperty(name = "data.directory")
    lateinit var dataDirectory: String

    @ConfigProperty(name = "product.repository")
    lateinit var repository: String

    lateinit var repoFile: File

    lateinit var products: List<Product>

    fun getProductList(): List<Product> {
        ensureRepositoryLoaded()
        return products
    }

    fun getProductById(id: String): Product? {
        ensureRepositoryLoaded()
        return products.find { it.id == id }
    }

    private fun ensureRepositoryLoaded() {
        if (!this::products.isInitialized) {
            val prod = mapper.readValue(getRepositoryFile(), object : TypeReference<List<Product>>() {})
            products = prod
        }
    }

    private fun getRepositoryFile(): File {
        if (!this::repoFile.isInitialized) {
            val dataDirectoryPath = Paths.get(dataDirectory)
            if (!Files.exists(dataDirectoryPath)) {
                Files.createDirectories(dataDirectoryPath)
            }
            repoFile = dataDirectoryPath.resolve(repository).toFile()
        }
        return repoFile
    }
}