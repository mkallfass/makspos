package de.mkallfass.tools.minipos.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.mkallfass.tools.minipos.domain.Order
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@ApplicationScoped
class OrderRepository {

    @ConfigProperty(name = "data.directory")
    lateinit var dataDirectory: String

    @ConfigProperty(name = "order.repository.file")
    lateinit var repositoryFile: String

    val mapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

    fun add(order: Order) {
        repositoryFile().appendText(mapper.writeValueAsString(order) + System.lineSeparator())
    }

    fun repositoryFile(): File {
        val dataDirectoryPath = Paths.get(dataDirectory)
        if (!Files.exists(dataDirectoryPath)) {
            Files.createDirectories(dataDirectoryPath)
        }
        return dataDirectoryPath.resolve(repositoryFile).toFile()
    }
}