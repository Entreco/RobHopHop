package nl.entreco.robhophop.monitor.di

import dagger.Module
import dagger.Provides
import nl.entreco.exchange_core.CurrentExchange
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService

@Module
object MonitorModule {

    @JvmStatic
    @Provides
    fun provideExchange(
        @CurrentExchange exchange: String,
        exchanges: Map<String, @JvmSuppressWildcards Exchange>
    ): Exchange {
        return exchanges.get(exchange)
            ?: throw IllegalStateException("Unable to find exchange $exchange")
    }

    @JvmStatic
    @Provides
    fun provideMonitoringService(
        @CurrentExchange exchange: String,
        exchanges: Map<String, @JvmSuppressWildcards ExchangeMonitoringService>
    ): ExchangeMonitoringService {
        return exchanges.get(exchange)
            ?: throw IllegalStateException("Unable to find service for $exchange")
    }
}