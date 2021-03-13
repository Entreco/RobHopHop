package nl.entreco.robhophop.pin

class PinModel {
}

sealed class PinEvent{
    object Go : PinEvent()
    object Check : PinEvent()
    object Fingerprint : PinEvent()
}