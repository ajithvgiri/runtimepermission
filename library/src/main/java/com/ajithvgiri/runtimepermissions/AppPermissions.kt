package com.ajithvgiri.runtimepermissions

import android.Manifest

class AppPermissions {
    companion object {

        // Body Sensors
        const val PERMISSION_BODY_SENSORS = Manifest.permission.BODY_SENSORS

        // Calendar
        const val PERMISSION_READ_CALENDAR = Manifest.permission.READ_CALENDAR
        const val PERMISSION_WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR

        // Call Log
        const val PERMISSION_READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
        const val PERMISSION_WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG

        // Camera
        const val PERMISSION_CAMERA = Manifest.permission.CAMERA

        // Contacts & Accounts
        const val PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS
        const val PERMISSION_WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS
        const val PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS

        // Location
        const val PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

        // Record Audio
        const val PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

        // SMS
        const val PERMISSION_SEND_SMS = Manifest.permission.SEND_SMS
        const val PERMISSION_READ_SMS = Manifest.permission.READ_SMS
        const val PERMISSION_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
        const val PERMISSION_RECEIVE_MMS = Manifest.permission.RECEIVE_MMS

        // Storage
        const val PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        // Phone & Call
        const val PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE
        const val PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE
        const val PERMISSION_PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS
    }
}