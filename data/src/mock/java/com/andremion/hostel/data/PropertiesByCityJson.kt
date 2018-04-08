package com.andremion.hostel.data

import com.andremion.hostel.data.remote.model.PropertiesByCity
import com.google.gson.Gson

object PropertiesByCityJson {

    fun getObjects(): PropertiesByCity {
        val properties = getJson()
        return Gson().fromJson(properties, PropertiesByCity::class.java)
    }

    fun getJson(): String {
        val stream = javaClass.classLoader.getResourceAsStream("response/properties.json")
        return stream.bufferedReader().use { it.readText() }
    }
}