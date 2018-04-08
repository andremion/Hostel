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

import com.andremion.hostel.data.entity.City
import com.andremion.hostel.data.local.model.CityLocal
import com.andremion.hostel.data.remote.model.CityRemote

class CityMapper {

    fun toLocalModel(cityRemote: CityRemote): CityLocal {
        return CityLocal(id = cityRemote.id,
                name = cityRemote.name,
                image = "https://ucd.hwstatic.com/hw/location-images/city/london_s.jpg",
                country = cityRemote.country)
    }

    fun toEntity(cityLocal: CityLocal): City {
        return City(cityLocal.id, cityLocal.name, cityLocal.image, cityLocal.country)
    }
}