package nl.entreco.robhophop.main.home.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import nl.entreco.exchange_core.Exchange

@Module
object HomeModule {

    @JvmStatic
    @Provides
    @ElementsIntoSet
    fun provideExchange(): Set<@JvmSuppressWildcards Exchange> = emptySet()

}