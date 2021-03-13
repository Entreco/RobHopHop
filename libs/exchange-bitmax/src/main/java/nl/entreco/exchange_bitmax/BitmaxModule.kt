package nl.entreco.exchange_bitmax

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object BitmaxModule {

    private val bitmax = Exchange.builder("Bitmax")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitmax")
    fun provideExchange(): Exchange = bitmax

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitmax")
    fun provideMonitoringService(): ExchangeMonitoringService =
        BitmaxMonitoringService(bitmax)
}