package nl.entreco.exchange_binance

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

class BinanceMonitoringService(
    private val exchange: Exchange
) : ExchangeMonitoringService {

    private val key by lazy { "CQsjGLA0qZa0FCaX5kNx6YNMJXv47nkrs0DjDlOnFYjDSmpZwjfBPKYR56R0gv4J" }
    private val secret by lazy { "Dq6wdCIKMswDe5hmk5W9LuKYrSWuK9YNLm4afnr3eTgTHdGRZoqckOY9wSn08FZP" }
    private val ws by lazy { ExchangeClientFactory.websocket(ExchangeVendor.BINANCE) }
    private val api by lazy { ExchangeClientFactory.http(ExchangeVendor.BINANCE).privateApi(key, secret) }

    override suspend fun start(): BigDecimal {
        return BigDecimal.ZERO
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun regular(currencies: List<CurrencyPair>): Flow<Monitor> = callbackFlow {
        ws.createTradeWebsocket(currencies).doOnNext {
            offer(Monitor(exchange, it.price, it.currencyPair))
        }.doOnError {
            close(it)
        }.subscribe()

        awaitClose {
            // trying to stop
        }
    }

    override suspend fun triangular(triangle: Triple<Currency, Currency, Currency>): Flow<Arbitrage> = callbackFlow {
        var arbitrage = Arbitrage(ExchangeVendor.BINANCE.name, triangle)
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

    // Binance TickData(price=54744.03000000, quantity=0.00032300, currencyPair=BTC-USDT, exchangeVendor=BINANCE, tradeSideType=BUY)
    // Binance TickData(price=54744.03000000, quantity=0.03143300, currencyPair=BTC-USDT, exchangeVendor=BINANCE, tradeSideType=BUY)

    override suspend fun trade(arbitrage: Arbitrage) {
        val start = BigDecimal(1)
        val first = arbitrage.pairs.get(0)
        val second = arbitrage.pairs.get(1)
        val third = arbitrage.pairs.get(2)

//        val result1 = api.order().marketOrder(first, TradeSideType.BUY, start)
//        val result2 = api.order().marketOrder(second, TradeSideType.SELL, start)
//        val result3 = api.order().marketOrder(third, TradeSideType.BUY, start)
    }

    override fun stop() {

    }
}