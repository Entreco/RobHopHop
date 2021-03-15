package nl.entreco.robhophop.main.trades.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_binance.BinanceModule
import nl.entreco.exchange_bitmex.BitmexModule
import nl.entreco.exchange_huobi.HuobiModule
import nl.entreco.exchange_unicornx.UnicornxModule
import nl.entreco.exchange_unicornx.UnicornxModule_ProvideExchangeFactory
import nl.entreco.robhophop.main.trades.TradesFragment
import nl.entreco.robhophop.main.trades.TradesViewModel

@Component(
    modules = [
        BinanceModule::class,
//        HuobiModule::class,
        UnicornxModule::class,
    ]
)
interface TradesComponent {

    fun viewModel(): TradesViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder
        fun build(): TradesComponent
    }
}

internal fun TradesFragment.component(): Lazy<TradesComponent> = lazy {
    DaggerTradesComponent.builder()
        .activity(requireActivity())
        .build()
}