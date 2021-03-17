package nl.entreco.exchange_upbit

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object UpbitModule {
    private val upbit = Exchange.builder("Upbit")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Upbit")
    fun provideExchange(): Exchange = upbit

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Upbit")
    fun provideMonitoringService(): ExchangeMonitoringService =
        UpbitMonitoringService(upbit)
}