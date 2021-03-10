package nl.entreco.robhophop.main.home.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_bitvavo.BitvavoModule
import nl.entreco.exchange_core.Exchange
import nl.entreco.robhophop.main.home.HomeFragment
import nl.entreco.robhophop.main.home.HomeNavigator
import nl.entreco.robhophop.main.home.HomeViewModel
import nl.entreco.robhophop.main.home.exchanges.HomeExchangeItemAdapter

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Component(modules = [HomeModule::class, BitvavoModule::class])
interface HomeComponent {

    fun exchange(): Set<@JvmSuppressWildcards Exchange>

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
