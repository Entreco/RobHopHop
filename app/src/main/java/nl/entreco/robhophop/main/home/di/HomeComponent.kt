package nl.entreco.robhophop.main.home.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_binance.BinanceModule
import nl.entreco.exchange_bitmax.BitmaxModule
import nl.entreco.exchange_bitvavo.BitvavoModule
import nl.entreco.exchange_huobi.HuobiModule
import nl.entreco.exchange_kraken.KrakenModule
import nl.entreco.exchange_unicornx.UnicornxModule
import nl.entreco.robhophop.main.home.HomeFragment
import nl.entreco.robhophop.main.home.HomeNavigator
import nl.entreco.robhophop.main.home.HomeViewModel
import nl.entreco.robhophop.main.home.exchanges.HomeExchangeItemAdapter

@Component(
    modules = [
        HomeModule::class,
        BinanceModule::class,
        BitmaxModule::class,
        BitvavoModule::class,
        HuobiModule::class,
        KrakenModule::class,
        UnicornxModule::class,
    ]
)
interface HomeComponent {

    fun viewModel(): HomeViewModel
    fun navigator(): HomeNavigator
    fun adapter(): HomeExchangeItemAdapter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder

        fun build(): HomeComponent
    }
}

internal fun HomeFragment.component(): Lazy<HomeComponent> = lazy {
    DaggerHomeComponent.builder()
        .activity(requireActivity())
        .build()
}
