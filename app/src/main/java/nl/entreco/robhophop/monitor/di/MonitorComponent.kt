package nl.entreco.robhophop.monitor.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nl.entreco.exchange_bitvavo.BitvavoModule
import nl.entreco.robhophop.monitor.MonitorActivity
import nl.entreco.robhophop.monitor.MonitorViewModel

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
@Component(modules = [MonitorModule::class, BitvavoModule::class])
interface MonitorComponent {

    fun viewModel(): MonitorViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(context: Context): Builder

        fun build(): MonitorComponent
    }
}

internal fun MonitorActivity.component(): Lazy<MonitorComponent> = lazy {
    DaggerMonitorComponent.builder()
        .activity(this)
        .build()
}