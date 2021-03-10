package nl.entreco.robhophop.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Launch Setup
        startActivity(Intent("nl.entreco.robhophop.Setup"))
        finish()
    }
}