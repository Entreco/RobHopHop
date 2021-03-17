package nl.entreco.robhophop.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.entreco.robhophop.RobKtx.hideSystemUi

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemUi()
        super.onCreate(savedInstanceState)
        // Launch Setup
        startActivity(Intent("nl.entreco.robhophop.Setup"))
        finish()
    }
}