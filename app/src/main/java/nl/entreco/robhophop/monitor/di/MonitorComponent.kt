package nl.entreco.robhophop.monitor.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_binance.BinanceModule
import nl.entreco.exchange_bitmax.BitmaxModule
import nl.entreco.exchange_bitvavo.BitvavoModule
import nl.entreco.exchange_core.CurrentExchange
import nl.entreco.exchange_huobi.HuobiModule
import nl.entreco.exchange_kraken.KrakenModule
import nl.entreco.exchange_unicornx.UnicornxModule
import nl.entreco.robhophop.monitor.MonitorActivity
import nl.entreco.robhophop.monitor.MonitorViewModel

@Component(
    modules = [
        MonitorModule::class,
        BinanceModule::class,
        BitmaxModule::class,
        BitvavoModule::class,
        HuobiModule::class,
        KrakenModule::class,
        UnicornxModule::class,
    ]
)
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