package nl.entreco.exchange_poloniex

import com.njkim.reactivecrypto.core.ExchangeClientFactory
import com.njkim.reactivecrypto.core.common.model.ExchangeVendor
import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import nl.entreco.exchange_core.Arbitrage
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import nl.entreco.exchange_core.Monitor
import java.math.BigDecimal

class PoloniexMonitoringService(
    private val exchange: Exchange
) : ExchangeMonitoringService {

    private val ws by lazy { ExchangeClientFactory.websocket(ExchangeVendor.POLONIEX) }

    override suspend fun start(): BigDecimal {
        return BigDecimal.ZERO
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun regular(currencies: List<CurrencyPair>): Flow<Monitor> = callbackFlow {
        val flux = ws.createTradeWebsocket(currencies)

        flux.doOnNext {
            offer(Monitor(exchange, it.price, it.currencyPair))
        }.doOnError {
            close(it)
        }.subscribe()

        awaitClose {
            // trying to stop
        }
    }

    override suspend fun triangular(triangle: Triple<Currency, Currency, Currency>): Flow<Arbitrage> = callbackFlow {

        var arbitrage = Arbitrage(ExchangeVendor.POLONIEX.name, triangle)

        ws.createTradeWebsocket(arbitrage.pairs).doOnNext {
            println("WOAH ${exchange.name} $it")
            arbitrage += it
            if (arbitrage.isFilled()) {
                offer(arbitrage.copy())
                arbitrage = arbitrage.reset()
            }
        }.subscribe()

        awaitClose { }
    }

    override fun stop() {

    }
}