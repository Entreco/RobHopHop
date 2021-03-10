package nl.entreco.robhophop.pin

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class PinModel {
}

sealed class PinEvent{
    object Go : PinEvent()
    object Check : PinEvent()
    object Fingerprint : PinEvent()
}