package de.mkallfass.tools.minipos.rest.endpoint

import de.mkallfass.tools.minipos.domain.LineItem
import de.mkallfass.tools.minipos.domain.Order
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.RestAssured.`when`
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class OrdersTest {
    @Test
    fun testOrdersEndpoint() {
        val lineItems = listOf(
            LineItem(id = "test-01", quantity = 4.5, price = 0.0),
            LineItem(id = "test-02", quantity = 4.5, price = 1.0))
        given()
            .contentType(ContentType.JSON)
            .body(Order(lineItems = lineItems))
        .`when`()
            .post("/api/orders")
        .then()
            .statusCode(201)
            .body(containsString("\"id\":"))
    }
}