package nl.entreco.exchange_bitmex

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object BitmexModule {
    private val binance = Exchange.builder("Bitmex")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitmex")
    fun provideExchange(): Exchange = binance

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitmex")
    fun provideMonitoringService(): ExchangeMonitoringService =
        BitmexMonitoringService(binance)
}