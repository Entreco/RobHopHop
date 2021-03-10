package nl.entreco.exchange_binance

import com.njkim.reactivecrypto.core.ExchangeClientFactory
import com.njkim.reactivecrypto.core.common.model.ExchangeVendor
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.BTC
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.USDT
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import nl.entreco.exchange_core.ExchangeMonitoringService
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
class BinanceMonitoringService : ExchangeMonitoringService {

    private val ws by lazy { ExchangeClientFactory.websocket(ExchangeVendor.BINANCE) }

    override suspend fun start(): BigDecimal {
        return BigDecimal.ZERO
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun monitor(): Flow<BigDecimal> = callbackFlow {
        val flux = ws.createTradeWebsocket(listOf(CurrencyPair(BTC, USDT)))

        flux.doOnNext {
            offer(it.price)
        }.doOnError {
            close(it)
        }.subscribe()

        awaitClose {
            // trying to stop
        }
    }

    override fun stop() {

    }
}