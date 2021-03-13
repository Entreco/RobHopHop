package nl.entreco.exchange_unicornx

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object UnicornxModule {

    private val unicornx = Exchange.builder("UnicornX")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("UnicornX")
    fun provideExchange(): Exchange = unicornx

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("UnicornX")
    fun provideMonitoringService(): ExchangeMonitoringService =
        UnicornxMonitoringService(unicornx)
}