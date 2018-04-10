package com.andremion.hostel.data

import com.andremion.hostel.data.remote.model.PropertiesByCity
import com.google.gson.Gson

object PropertiesByCityJson {

    private val gson = Gson()

    fun getObjects(): PropertiesByCity {
        val properties = getJson()
        return gson.fromJson(properties, PropertiesByCity::class.java)
    }

    fun getJson(): String {
        val stream = javaClass.classLoader.getResourceAsStream("response/properties.json")
        return stream.bufferedReader().use { it.readText() }
    }

    fun getEmptyObjects(): PropertiesByCity {
        val properties = getJson()
        val propertiesByCity = gson.fromJson(properties, PropertiesByCity::class.java)
        return PropertiesByCity(listOf(), propertiesByCity.location)
    }

    fun getEmptyJson(): String {
        return gson.toJson(getEmptyObjects(), PropertiesByCity::class.java)
    }
}