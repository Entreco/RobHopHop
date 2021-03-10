package nl.entreco.robhophop.setup

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import javax.inject.Inject

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class SetupNavigator @Inject constructor(
    private val context: Context
) : Observer<SetupEvent> {

    override fun onChanged(event: SetupEvent?) {
        when(event){
            SetupEvent.Go -> context.startActivity(Intent("nl.entreco.robhophop.Main"))
        }
    }
}