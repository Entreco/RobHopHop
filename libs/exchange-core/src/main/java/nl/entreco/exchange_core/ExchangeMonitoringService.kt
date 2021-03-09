package nl.entreco.exchange_core

import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
interface ExchangeMonitoringService {
    suspend fun start(): BigDecimal
    suspend fun monitor(): Flow<BigDecimal>
    fun stop()
}