package nl.entreco.robhophop.main.arbitrage

import nl.entreco.robhophop.main.arbitrage.trades.triangular.TriangularTrade
import org.junit.Assert.assertEquals
import org.junit.Test

class TriangularTradeTest {

    @Test
    fun `calculate profit`() {
        val subject = TriangularTrade(
            "1",
            "Test",
            Triple(
                "EUR",
                "GBP",
                "USD"
            ),
            Triple(0.9f, 1.5f, 1.7f),
            Triple(0.00f, 0.00f, 0.00f)
        ){}

        assertEquals("+2.0000 %", subject.displayProfit)
    }

    @Test
    fun `calculate profit with fees`() {
        val subject = TriangularTrade(
            "2",
            "Test",
            Triple(
                "EUR",
                "GBP",
                "USD"
            ),
            Triple(0.9f, 1.5f, 1.7f),
            Triple(0.25f, 0.25f, 0.25f)
        ){}

        assertEquals("+1.2500 %", subject.displayProfit)
    }
}