package nl.entreco.exchange_huobi

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import javax.inject.Named

@Module
object HuobiModule {

    private val huobi = Exchange.builder("Huobi")
        .withMarket("BTC-USDT")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Huobi")
    fun provideExchange(): Exchange = huobi

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Huobi")
    fun provideMonitoringService(): ExchangeMonitoringService =
        HuobiMonitoringService(huobi)
}