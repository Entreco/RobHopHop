package nl.entreco.robhophop.main.profile.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.robhophop.main.profile.ProfileFragment
import nl.entreco.robhophop.main.profile.ProfileViewModel

@Component
interface ProfileComponent {

    fun viewModel(): ProfileViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder
        fun build(): ProfileComponent
    }
}

internal fun ProfileFragment.component(): Lazy<ProfileComponent> = lazy {
    DaggerProfileComponent.builder()
        .activity(requireActivity())
        .build()
}