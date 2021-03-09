package nl.entreco.exchange_bitvavo

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
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
object BitvavoModule {

    @JvmStatic
    @Provides
    @IntoSet
    fun provideExchange(): Exchange = Exchange.builder("Bitvavo")
        .withKey("key")
        .withSecret("secret")
        .build()

    @JvmStatic
    @Provides
    fun provideMonitoringService(): ExchangeMonitoringService = BitvavoMonitoringService()

}