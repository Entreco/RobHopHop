package nl.entreco.exchange_okex

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object OkexModule {
    private val okex = Exchange.builder("Okex")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Okex")
    fun provideExchange(): Exchange = okex

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Okex")
    fun provideMonitoringService(): ExchangeMonitoringService =
        OkexMonitoringService(okex)
}