package nl.entreco.exchange_unicornx

import com.njkim.reactivecrypto.core.ExchangeClientFactory
import com.njkim.reactivecrypto.core.common.model.ExchangeVendor
import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import java.math.BigDecimal

class UnicornxMonitoringService(
    private val exchange: Exchange
) : ExchangeMonitoringService {

    private val ws by lazy { ExchangeClientFactory.websocket(ExchangeVendor.UNICORNX) }

    override suspend fun start(): BigDecimal {
        return BigDecimal.ZERO
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun monitor(): Flow<Pair<Exchange, BigDecimal>> = callbackFlow {
        val flux = ws.createTradeWebsocket(listOf(CurrencyPair(Currency.BTC, Currency.USDT)))

        flux.doOnNext {
            offer(Pair(exchange, it.price))
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