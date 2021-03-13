package nl.entreco.exchange_bitvavo

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object BitvavoModule {

    private val bitvavo = Exchange.builder("Bitvavo")
        .withMarket("BTC-EUR")
        .withCurrency("EUR")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitvavo")
    fun provideExchange(): Exchange = bitvavo

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitvavo")
    fun provideMonitoringService(): ExchangeMonitoringService =
        BitvavoMonitoringService(bitvavo)

}