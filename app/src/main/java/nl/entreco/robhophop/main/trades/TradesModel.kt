package nl.entreco.robhophop.main.trades

import nl.entreco.exchange_core.NoExchange
import nl.entreco.robhophop.main.dashboard.Action
import java.math.BigDecimal

data class TradesModel(
    val exchange1: String = NoExchange().name,
    val exchange2: String = NoExchange().name,
    val trading1: BigDecimal = BigDecimal.ZERO,
    val trading2: BigDecimal = BigDecimal.ZERO,
    val action1: Action = Action.None,
    val action2: Action = Action.None,
    val expectedProfit: BigDecimal = BigDecimal.ZERO
)