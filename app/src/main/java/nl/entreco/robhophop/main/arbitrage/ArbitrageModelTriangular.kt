package nl.entreco.robhophop.main.arbitrage

import nl.entreco.robhophop.main.arbitrage.trades.triangular.TriangularTrade

data class ArbitrageModelTriangular(
    val options: List<TriangularTrade> = emptyList()
)