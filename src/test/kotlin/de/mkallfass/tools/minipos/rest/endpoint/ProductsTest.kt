package de.mkallfass.tools.minipos.rest.endpoint

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class ProductsTest {
    @Test
    fun testOrdersEndpoint() {
        // @formatter:off
        given()
        .`when`()
            .get("/api/products")
        .then()
            .statusCode(200)
            .body(containsString("TEST-01"))
        // @formatter:on
    }
}