package de.mkallfass.makspos.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.mkallfass.makspos.domain.Order
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@ApplicationScoped
class OrderRepository {

    @ConfigProperty(name = "data.directory")
    lateinit var dataDirectory: String

    @ConfigProperty(name = "order.repository")
    lateinit var repository: String

    lateinit var repoFile: File

    val mapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

    fun add(order: Order) {
        getRepositoryFile().appendText(mapper.writeValueAsString(order) + System.lineSeparator())
    }

    fun getAll(): List<Order> {
        val orders = ArrayList<Order>()
        getRepositoryFile().useLines { lines ->
            lines.forEach {
                orders.add(mapper.readValue(it, Order::class.java))
            }
        }
        return orders
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