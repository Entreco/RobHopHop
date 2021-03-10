package nl.entreco.robhophop.setup.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.robhophop.setup.SetupActivity
import nl.entreco.robhophop.setup.SetupNavigator
import nl.entreco.robhophop.setup.SetupViewModel

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Component
interface SetupComponent {

    fun viewModel(): SetupViewModel
    fun navigator(): SetupNavigator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder

        fun build(): SetupComponent
    }
}

internal fun SetupActivity.component(): Lazy<SetupComponent> = lazy {
    DaggerSetupComponent.builder()
        .activity(this)
        .build()
}