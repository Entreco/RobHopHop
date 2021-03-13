package nl.entreco.exchange_core

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface ExchangeMonitoringService {
    suspend fun start(): BigDecimal
    suspend fun monitor(): Flow<Pair<Exchange, BigDecimal>>
    fun stop()
}