package nl.entreco.exchange_core

interface Exchange {
    val name: String
    val market: String
    val currency: String
    val logo: Int
        get() = 0

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

    override val logo: Int
        get() = builder.logo
}


class ExchangeBuilder(internal val name: String) {

    internal var market: String? = null
    internal var currency: String = "USDT"
    internal var logo: Int = 0

    fun withMarket(market: String) = apply {
        this.market = market
    }

    fun withCurrency(currency: String) = apply {
        this.currency = currency
    }

    fun withLogo(logo: Int) = apply {
        this.logo = logo
    }

    fun build(): Exchange {
        requireNotNull(market)
        return ExchangeImpl(this)
    }

}