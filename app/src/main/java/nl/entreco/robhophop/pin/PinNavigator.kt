package nl.entreco.robhophop.pin

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import nl.entreco.robhophop.R
import javax.inject.Inject

class PinNavigator @Inject constructor(
    private val context: FragmentActivity,
    private val promptBuilder: BiometricPrompt.PromptInfo.Builder,
) : BiometricPrompt.AuthenticationCallback(), Observer<PinEvent> {

    private val prompt by lazy {
        BiometricPrompt(context, ContextCompat.getMainExecutor(context), this)
    }

    private val promptInfo by lazy {
        promptBuilder
            .setTitle(context.getString(R.string.prompt_title))
            .setSubtitle(context.getString(R.string.prompt_subtitle))
            .setNegativeButtonText(context.getString(R.string.prompt_cancel))
            .build()
    }

    override fun onChanged(event: PinEvent?) {
        when (event) {
            PinEvent.Go -> launchMain()
            PinEvent.Check -> checkBiometricStatus()
            PinEvent.Fingerprint -> showFingerPrint()
        }
    }

    private fun checkBiometricStatus() {
        when (BiometricManager.from(context).canAuthenticate(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> showFingerPrint()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> launchEnrollment()
            else -> Toast.makeText(context, R.string.biometry_unavailable, Toast.LENGTH_LONG).show()
        }
    }

    private fun launchEnrollment() {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_WEAK or DEVICE_CREDENTIAL
            )
        }
        context.startActivityForResult(enrollIntent, 1234)
    }

    private fun showFingerPrint() {
        prompt.authenticate(promptInfo)
    }

    private fun launchMain() {
        context.startActivity(Intent("nl.entreco.robhophop.Main"))
        context.finishAfterTransition()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        launchMain()
    }
}