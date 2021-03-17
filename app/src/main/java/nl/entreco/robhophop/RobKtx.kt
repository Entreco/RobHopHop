package nl.entreco.robhophop

import android.app.Activity
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object RobKtx {

    inline fun <reified VM : ViewModel> AppCompatActivity.viewModelProvider(
        crossinline provider: () -> VM
    ): Lazy<VM> = lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return provider() as T
            }
        }).get(VM::class.java)
    }

    inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        crossinline provider: () -> VM
    ): Lazy<VM> = lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return provider() as T
            }
        }).get(VM::class.java)
    }

    fun Activity.hideSystemUi() {
        window.setDecorFitsSystemWindows(false)
    }
}