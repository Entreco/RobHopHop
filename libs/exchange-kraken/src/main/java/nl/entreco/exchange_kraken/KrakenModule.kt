package nl.entreco.exchange_kraken

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object KrakenModule {

    private val kraken = Exchange.builder("Kraken")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Kraken")
    fun provideExchange(): Exchange = kraken

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Kraken")
    fun provideMonitoringService(): ExchangeMonitoringService =
        KrakenMonitoringService(kraken)
}