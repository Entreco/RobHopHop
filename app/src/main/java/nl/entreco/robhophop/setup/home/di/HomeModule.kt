package nl.entreco.robhophop.setup.home.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import nl.entreco.exchange_core.Exchange

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
object HomeModule {

    @JvmStatic
    @Provides
    @ElementsIntoSet
    fun provideExchange(): Set<@JvmSuppressWildcards Exchange> = emptySet()

}