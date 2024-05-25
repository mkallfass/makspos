package de.mkallfass.tools.minipos.service

import de.mkallfass.tools.minipos.domain.Product
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ProductService {

    @Inject
    lateinit var productRepository: ProducRepository

    fun getProductList(): List<Product> {
        return productRepository.getProductList()
    }

    fun getProductById(id: String): Product? {
        return productRepository.getProductById(id)
    }
}