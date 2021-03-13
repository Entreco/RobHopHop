package nl.entreco.robhophop.main.home.exchanges

data class HomeExchangeItem(
    private val position: Int,
    val name: String
) {
    override fun toString() = name
}