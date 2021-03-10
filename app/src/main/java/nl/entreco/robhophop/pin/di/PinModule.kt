package nl.entreco.robhophop.pin.di

import androidx.biometric.BiometricPrompt
import dagger.Module
import dagger.Provides

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Module
object PinModule {

    @JvmStatic
    @Provides
    fun providePromptBuilder() = BiometricPrompt.PromptInfo.Builder()
}