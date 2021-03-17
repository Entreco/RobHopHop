package nl.entreco.exchange_core

import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import java.math.BigDecimal

data class Monitor(
    val exchange: Exchange,
    val price: BigDecimal,
    val pair: CurrencyPair
)