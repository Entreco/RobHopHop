package nl.entreco.robhophop.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Launch Setup
        startActivity(Intent("nl.entreco.robhophop.Setup"))
        finish()
    }
}