/*
 * Copyright (c) 2018. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andremion.hostel.data.repository.mapper

import com.andremion.hostel.data.entity.Property
import com.andremion.hostel.data.local.model.PropertyLocal
import com.andremion.hostel.data.remote.model.*

class PropertyMapper {

    fun toLocalModel(city: Int, items: List<PropertyRemote>) = items.map { toLocalModel(city, it) }

    private fun toLocalModel(city: Int, propertyRemote: PropertyRemote): PropertyLocal {
        return PropertyLocal(propertyRemote.id,
                city,
                propertyRemote.name,
                propertyRemote.isFeatured,
                parseImages(propertyRemote.images),
                propertyRemote.overview,
                parseFacilities(propertyRemote.facilities),
                parsePrice(propertyRemote.lowestPricePerNight),
                parseRating(propertyRemote.overallRating))
    }

    private fun parseImages(images: List<ImageRemote>): List<String> {
        return images
                .map {
                    var image = "${it.prefix}${it.suffix}"
                    if (!image.startsWith("http")) {
                        image = "http://$image"
                    }
                    image
                }
    }

    private fun parseFacilities(facilities: List<FacilitiesRemote>): List<String> {
        return facilities
                .filter { it.name == "Free" }
                .flatMap { it.facilities }
                .map { it.name }
    }

    private fun parsePrice(priceRemote: PriceRemote) = priceRemote.value / 7.55

    private fun parseRating(ratingRemote: RatingRemote?): Float? {
        return ratingRemote?.let {
            val rating = it.overall * 10 / 100f
            if (rating > 0) rating else null
        }
    }

    fun toEntity(items: List<PropertyLocal>) = items.map { toEntity(it) }

    private fun toEntity(propertyLocal: PropertyLocal): Property {
        return Property(propertyLocal.id,
                propertyLocal.city,
                propertyLocal.name,
                propertyLocal.featured,
                propertyLocal.images.first(),
                propertyLocal.overview,
                propertyLocal.facilities.joinToString(", "),
                "€%.0f".format(propertyLocal.price),
                propertyLocal.rating?.toString() ?: "")
    }
}