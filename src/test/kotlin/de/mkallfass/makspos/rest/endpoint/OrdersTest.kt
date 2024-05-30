package de.mkallfass.makspos.rest.endpoint

import de.mkallfass.makspos.domain.LineItem
import de.mkallfass.makspos.domain.Order
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class OrdersTest {
    @Test
    fun testOrdersEndpoint() {
        val lineItems = listOf(
            LineItem(id = "TEST-01", quantity = 4.5),
            LineItem(id = "TEST-02", quantity = 4.5)
        )
        // @formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(Order(lineItems = lineItems))
        .`when`()
            .post("/api/orders")
        .then()
            .statusCode(201)
            .body(containsString("\"id\":"))
        // @formatter:on
    }
}