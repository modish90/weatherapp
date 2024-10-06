package com.example.weatherapp.network_layer

import java.util.*
import javax.net.ssl.HostnameVerifier

object CustomHostNameVerifier : HostnameVerifier {
    private val whiteListed = listOf<String>("")
    override fun verify(hostName: String?, p1: javax.net.ssl.SSLSession?): Boolean {
        return whiteListed.map {
            hostName?.toLowerCase(Locale.US)?.contains(it)
        }.find { it == true } == true
    }
}