package de.mkallfass.makspos.service

import de.mkallfass.makspos.domain.Product
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ProductService {

    @Inject
    lateinit var productRepository: ProductRepository

    fun getProductList(): List<Product> {
        return productRepository.getProductList()
    }

    fun getProductById(id: String): Product? {
        return productRepository.getProductById(id)
    }
}