package nl.entreco.exchange_core

import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.BTC
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.ETH
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.USD
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.USDT
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal

interface ExchangeMonitoringService {
    suspend fun start(): BigDecimal
    suspend fun regular(currencies: List<CurrencyPair> = listOf(CurrencyPair(BTC, USD))): Flow<Monitor>
    suspend fun triangular(triangle: Triple<Currency, Currency, Currency> = Triple(ETH, BTC, USDT)): Flow<Arbitrage> = flow {  }
    suspend fun trade(triangle: Arbitrage){}
    fun stop()
}