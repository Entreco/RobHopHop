package nl.entreco.robhophop.pin.di

import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Component
import nl.entreco.robhophop.pin.PinActivity
import nl.entreco.robhophop.pin.PinNavigator
import nl.entreco.robhophop.pin.PinViewModel

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Component(modules = [PinModule::class])
interface PinComponent {

    fun viewModel(): PinViewModel
    fun navigator(): PinNavigator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: FragmentActivity): Builder

        fun build(): PinComponent
    }
}

internal fun PinActivity.component(): Lazy<PinComponent> = lazy {
    DaggerPinComponent.builder()
        .activity(this)
        .build()
}