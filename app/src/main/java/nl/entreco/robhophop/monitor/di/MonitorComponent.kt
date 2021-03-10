package nl.entreco.robhophop.monitor.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_binance.BinanceModule
import nl.entreco.exchange_bitvavo.BitvavoModule
import nl.entreco.exchange_core.CurrentExchange
import nl.entreco.robhophop.monitor.MonitorActivity
import nl.entreco.robhophop.monitor.MonitorViewModel

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Component(modules = [
    MonitorModule::class,
    BinanceModule::class,
    BitvavoModule::class,
])
interface MonitorComponent {

    fun viewModel(): MonitorViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder

        @BindsInstance
        fun exchange(@CurrentExchange exchange: String): Builder

        fun build(): MonitorComponent
    }
}

internal fun MonitorActivity.component(): Lazy<MonitorComponent> = lazy {
    val exchange =
        intent.getStringExtra("extra_exchange") ?: throw IllegalStateException("Pass an Exchange")
    DaggerMonitorComponent.builder()
        .activity(this)
        .exchange(exchange)
        .build()
}