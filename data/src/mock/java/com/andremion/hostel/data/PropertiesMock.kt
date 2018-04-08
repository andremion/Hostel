package com.andremion.hostel.data

import com.andremion.hostel.data.entity.Property
import com.andremion.hostel.data.local.model.CityLocal
import com.andremion.hostel.data.local.model.PropertyLocal
import com.andremion.hostel.data.remote.model.*

object PropertiesMock {

    fun getProperties(city: Int): List<Property> {
        return (1..2).map {
            Property(id = it,
                    city = city,
                    name = "name$it",
                    featured = it % 2 == 0,
                    image = (1..5).map { "http://image$it.jpg" }.first(),
                    overview = "overview$it",
                    facilities = (1..5).joinToString(", ") { "facility$it" },
                    price = "€%.0f".format(it * 1.0),
                    rating = (it * 1f).toString())
        }.sortedWith(
                compareByDescending<Property> { it.featured }
                        .thenByDescending { it.rating }
        )
    }

    fun getLocalCity(id: Int): CityLocal {
        return CityLocal(id = id, name = "name",
                image = "https://ucd.hwstatic.com/hw/location-images/city/london_s.jpg",
                country = "country")
    }

    fun getLocalProperties(city: Int): List<PropertyLocal> {
        return (1..2).map {
            PropertyLocal(id = it,
                    city = city,
                    name = "name$it",
                    featured = it % 2 == 0,
                    images = (1..5).map { "http://image$it.jpg" },
                    overview = "overview$it",
                    facilities = (1..5).map { "facility$it" },
                    price = it * 1.0,
                    rating = it * 1f)
        }.sortedWith(
                compareByDescending<PropertyLocal> { it.featured }
                        .thenByDescending { it.rating }
        )
    }

    fun getPropertiesByCity(cityId: Int): PropertiesByCity {
        val properties = getRemoteProperties()
        val city = getRemoteCity(cityId)
        val location = LocationRemote(city)
        return PropertiesByCity(properties, location)
    }

    private fun getRemoteCity(id: Int): CityRemote {
        return CityRemote(id = id, name = "name", country = "country")
    }

    private fun getRemoteProperties(): List<PropertyRemote> {
        return (1..2).map {
            PropertyRemote(id = it,
                    name = "name$it",
                    isFeatured = it % 2 == 0,
                    images = (1..5).map { ImageRemote("image$it", ".jpg") },
                    overview = "overview$it",
                    facilities = listOf("Free", "General").map {
                        FacilitiesRemote(id = it,
                                name = it,
                                facilities = (1..5).map { FacilityRemote("$it", "facility$it") }
                        )
                    },
                    lowestPricePerNight = PriceRemote(it * 7.55, "currency"),
                    overallRating = RatingRemote(it * 10, it * 2))
        }.sortedWith(
                compareByDescending<PropertyRemote> { it.isFeatured }
                        .thenByDescending { it.overallRating?.overall }
        )
    }
}