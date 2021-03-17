package nl.entreco.robhophop.main.arbitrage.di

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
import nl.entreco.robhophop.main.arbitrage.ArbitrageFragment
import nl.entreco.robhophop.main.arbitrage.trades.triangular.TriangularAdapter
import nl.entreco.robhophop.main.arbitrage.ArbitrageViewModel
import nl.entreco.robhophop.main.arbitrage.trades.regular.RegularAdapter

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
interface ArbitrageComponent {

    fun viewModel(): ArbitrageViewModel
    fun regularAdapter(): RegularAdapter
    fun triangleAdapter(): TriangularAdapter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder
        fun build(): ArbitrageComponent
    }
}

internal fun ArbitrageFragment.component(): Lazy<ArbitrageComponent> = lazy {
    DaggerArbitrageComponent.builder()
        .activity(requireActivity())
        .build()
}