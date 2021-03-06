package nl.entreco.robhophop.main.dashboard.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_binance.BinanceModule
import nl.entreco.exchange_bitmax.BitmaxModule
import nl.entreco.exchange_bitmex.BitmexModule
import nl.entreco.exchange_huobi.HuobiModule
import nl.entreco.exchange_kraken.KrakenModule
import nl.entreco.exchange_okex.OkexModule
import nl.entreco.exchange_poloniex.PoloniexModule
import nl.entreco.exchange_unicornx.UnicornxModule
import nl.entreco.exchange_upbit.UpbitModule
import nl.entreco.robhophop.main.dashboard.DashboardFragment
import nl.entreco.robhophop.main.dashboard.DashboardListAdapter
import nl.entreco.robhophop.main.dashboard.DashboardViewModel

@Component(
    modules = [
        BinanceModule::class,
        BitmaxModule::class,
        BitmexModule::class,
        HuobiModule::class,
        KrakenModule::class,
        OkexModule::class,
        PoloniexModule::class,
        UpbitModule::class,
        UnicornxModule::class,
    ]
)
interface DashboardComponent {

    fun viewModel(): DashboardViewModel
    fun adapter(): DashboardListAdapter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder
        fun build(): DashboardComponent
    }
}

internal fun DashboardFragment.component(): Lazy<DashboardComponent> = lazy {
    DaggerDashboardComponent.builder()
        .activity(requireActivity())
        .build()
}