package nl.entreco.exchange_poloniex

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object PoloniexModule {
    private val okex = Exchange.builder("Poloniex")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Poloniex")
    fun provideExchange(): Exchange = okex

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Poloniex")
    fun provideMonitoringService(): ExchangeMonitoringService =
        PoloniexMonitoringService(okex)
}