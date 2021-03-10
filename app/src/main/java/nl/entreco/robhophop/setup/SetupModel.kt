package nl.entreco.robhophop.setup

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class SetupModel {
}

sealed class SetupEvent{
    object Go : SetupEvent()
}