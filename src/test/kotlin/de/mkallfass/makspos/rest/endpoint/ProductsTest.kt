package de.mkallfass.makspos.rest.endpoint

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class ProductsTest {
    @Test
    fun testProductsEndpoint() {
        // @formatter:off
        given()
        .`when`()
            .get("/api/products")
        .then()
            .statusCode(200)
            .body(containsString("TEST-01"))
            .body(containsString("TEST-02"))
            .body(containsString("TEST-03"))
        // @formatter:on
    }
}