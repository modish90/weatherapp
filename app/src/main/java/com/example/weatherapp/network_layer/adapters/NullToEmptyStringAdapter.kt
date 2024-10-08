package com.example.weatherapp.network_layer.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

object NullToEmptyStringAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}