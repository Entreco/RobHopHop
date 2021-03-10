package nl.entreco.exchange_binance

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Module
object BinanceModule {

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Binance")
    fun provideExchange(): Exchange = Exchange.builder("Binance")
        .withKey("key")
        .withSecret("secret")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Binance")
    fun provideMonitoringService(): ExchangeMonitoringService = BinanceMonitoringService()
}