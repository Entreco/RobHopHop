package nl.entreco.exchange_core

interface Exchange {
    val name: String
    val market: String
    val currency: String

    companion object {
        fun builder(name: String) = ExchangeBuilder(name)
    }
}

internal class ExchangeImpl(
    private val builder: ExchangeBuilder
) : Exchange {

    override val name: String
        get() = builder.name

    override val market: String
        get() = builder.market!!

    override val currency: String
        get() = builder.currency
}


class ExchangeBuilder(internal val name: String) {

    internal var market: String? = null
    internal var currency: String = "USDT"

    fun withMarket(market: String) = apply {
        this.market = market
    }

    fun withCurrency(currency: String) = apply {
        this.currency = currency
    }

    fun build(): Exchange {
        requireNotNull(market)
        return ExchangeImpl(this)
    }

}