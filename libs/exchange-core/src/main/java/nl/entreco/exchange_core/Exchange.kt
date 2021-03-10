package nl.entreco.exchange_core

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
interface Exchange {
    val name: String

    companion object {
        fun builder(name: String) = ExchangeBuilder(name)
    }
}

internal class ExchangeImpl(
    private val builder: ExchangeBuilder
) : Exchange {
    override val name: String
        get() = builder.name
}


class ExchangeBuilder(internal val name: String) {

    private var key: String? = null
    private var secret: String? = null

    fun withKey(apiKey: String) = apply {
        key = apiKey
    }

    fun withSecret(apiSecret: String) = apply {
        secret = apiSecret
    }

    fun build(): Exchange {
        requireNotNull(key)
        requireNotNull(secret)
        return ExchangeImpl(this)
    }

}