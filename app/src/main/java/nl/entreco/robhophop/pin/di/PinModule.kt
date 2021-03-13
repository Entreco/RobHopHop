package nl.entreco.robhophop.pin.di

import androidx.biometric.BiometricPrompt
import dagger.Module
import dagger.Provides

@Module
object PinModule {

    @JvmStatic
    @Provides
    fun providePromptBuilder() = BiometricPrompt.PromptInfo.Builder()
}