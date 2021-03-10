package nl.entreco.exchange_bitvavo

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeModule
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
object BitvavoModule  {

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitvavo")
    fun provideExchange(): Exchange = Exchange.builder("Bitvavo")
        .withKey("key")
        .withSecret("secret")
        .build()

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("Bitvavo")
    fun provideMonitoringService(): ExchangeMonitoringService = BitvavoMonitoringService()

}