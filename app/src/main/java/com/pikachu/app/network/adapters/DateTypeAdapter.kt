package com.pikachu.app.network.adapters

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

public class DateTypeAdapter : TypeAdapter<Date>() {
    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    override fun write(out: JsonWriter?, value: Date?) {
        out?.jsonValue(gson.toJson(value))
    }

    override fun read(`in`: JsonReader?): Date {
        return gson.fromJson(`in`?.nextString(), Date::class.java)
    }
}