package nl.entreco.robhophop.setup.home.exchanges

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
data class HomeExchangeItem(
    private val position: Int,
    val name: String
) {
    override fun toString() = name
}