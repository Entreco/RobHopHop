package nl.entreco.robhophop.monitor

import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

data class MonitorModel(
    val valueDescription: String,
    val currentValue: BigDecimal,
    val currency: String
) {
    fun random(): MonitorModel {
        val delta: Int = Random.nextInt(-1, 1)
        val updatedValue: BigDecimal = currentValue + BigDecimal(delta)
        return copy(
            valueDescription = updatedValue.toEngineeringString(),
            currentValue = updatedValue
        )
    }

    fun init(update: BigDecimal): MonitorModel {
        val delta = currentValue + update
        return copy(
            valueDescription = delta.toEngineeringString(),
            currentValue = delta)
    }

    fun update(update: BigDecimal): MonitorModel = copy(
        valueDescription = update.toEngineeringString(),
        currentValue = update)
}

sealed class MonitorEvent {}