package de.mkallfass.makspos.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.mkallfass.makspos.domain.Product
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@ApplicationScoped
class ProductRepository {

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
            if (validateProductRepository(prod)) {
                products = prod
            }
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

    private fun validateProductRepository(products: List<Product>): Boolean {
        // Check uniqueness of product ids
        if (!products.allUniqueBy { it.id }) {
            Log.error("Not all ids in repository file are unique!")
            return false
        }
        return true
    }

    private inline fun <T, R> Iterable<T>.allUniqueBy(transform: (T) -> R): Boolean {
        val hashset = hashSetOf<R>()
        return this.all { hashset.add(transform(it)) }
    }
}