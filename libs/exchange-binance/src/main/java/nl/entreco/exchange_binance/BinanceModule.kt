package nl.entreco.exchange_binance

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object BinanceModule {

    private val binance = Exchange.builder("Binance")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Binance")
    fun provideExchange(): Exchange = binance

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Binance")
    fun provideMonitoringService(): ExchangeMonitoringService =
        BinanceMonitoringService(binance)
}