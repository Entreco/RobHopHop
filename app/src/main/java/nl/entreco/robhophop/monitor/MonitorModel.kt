package nl.entreco.robhophop.monitor

import java.math.BigDecimal
import kotlin.random.Random

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
data class MonitorModel(
    val valueDescription: String,
    val currentValue: BigDecimal
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