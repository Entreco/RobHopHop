package nl.entreco.robhophop.setup.home.exchanges

import android.content.Context
import android.widget.ArrayAdapter
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
class HomeExchangeItemAdapter @Inject constructor(
    context: Context,
) : ArrayAdapter<HomeExchangeItem>(context, android.R.layout.simple_spinner_item) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    fun submit(exchanges: List<HomeExchangeItem>) {
        clear()
        addAll(exchanges)
        notifyDataSetChanged()
    }

}