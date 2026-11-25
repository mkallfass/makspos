package de.mkallfass.makspos.service

import de.mkallfass.makspos.domain.LineItem
import de.mkallfass.makspos.domain.Order
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * Pins the on-disk JSON contract of the order repository.
 *
 * Up to Jackson 2 the repository mapper wrote the order date as a numeric epoch value; since
 * Jackson 3 it writes an ISO-8601 string. `orders.jsonl` is append-only, so both forms occur in
 * the same file and both have to stay readable.
 */
class OrderRepositoryMapperTest {

    private val mapper = OrderRepository().mapper

    @Test
    fun readsLegacyNumericDate() {
        val line = """
            {"id":"6d4f1870-87f6-490a-8b87-a81cefeeda43","date":1729262549.867011114,
            "lineitems":[{"id":"TEST-01","name":"Test01","price":0.05,"quantity":4.5,"total":0.225}],
            "total":0.225}
        """.trimIndent().replace("\n", "")

        val order = mapper.readValue(line, Order::class.java)

        assertEquals(Instant.ofEpochSecond(1729262549, 867011114), order.date!!.toInstant())
        assertEquals(0.225, order.total)
        assertEquals(1, order.lineitems.size)
    }

    @Test
    fun readsIsoDate() {
        val line = """
            {"id":"37620989-3b14-40fd-b14c-c9340452ee00","date":"2026-11-25T20:49:46.726516843+01:00",
            "lineitems":[{"id":"TEST-01","name":"Test01","price":0.05,"quantity":4.5,"total":0.225}],
            "total":0.225}
        """.trimIndent().replace("\n", "")

        val order = mapper.readValue(line, Order::class.java)

        assertEquals(Instant.parse("2026-11-25T19:49:46.726516843Z"), order.date!!.toInstant())
    }

    @Test
    fun writesIsoDate() {
        val order = Order(
            id = "11111111-2222-3333-4444-555555555555",
            date = ZonedDateTime.of(2026, 11, 25, 20, 49, 46, 726516843, ZoneOffset.ofHours(1)),
            lineitems = listOf(LineItem(id = "TEST-01", name = "Test01", price = 0.05, quantity = 4.5, total = 0.225)),
            total = 0.225,
        )

        val json = mapper.writeValueAsString(order)

        assertTrue(
            json.contains("\"date\":\"2026-11-25T20:49:46.726516843+01:00\""),
            "Expected an ISO-8601 date, got: $json",
        )
    }
}
