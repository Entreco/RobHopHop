package nl.entreco.charts

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface Chart {
    suspend fun stream(): Flow<BigDecimal>
}