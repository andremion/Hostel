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

package com.andremion.hostel.data.repository

import com.andremion.hostel.data.entity.City
import com.andremion.hostel.data.entity.Property
import com.andremion.hostel.data.local.CityLocalDataSource
import com.andremion.hostel.data.local.PropertyLocalDataSource
import com.andremion.hostel.data.remote.PropertyRemoteDataSource
import com.andremion.hostel.data.repository.mapper.CityMapper
import com.andremion.hostel.data.repository.mapper.PropertyMapper
import com.andremion.hostel.scheduler.AppSchedulers
import io.reactivex.Flowable
import io.reactivex.Maybe

class HostelRepository(
        private val appSchedulers: AppSchedulers,
        private val cityLocalDataSource: CityLocalDataSource,
        private val propertyLocalDataSource: PropertyLocalDataSource,
        private val propertyRemoteDataSource: PropertyRemoteDataSource,
        private val cityMapper: CityMapper,
        private val propertyMapper: PropertyMapper) {

    fun getById(id: Int): Flowable<City> {
        return cityLocalDataSource.getById(id)
                .map(cityMapper::toEntity)
                .subscribeOn(appSchedulers.io)
    }

    fun findByCity(city: Int, refresh: Boolean = false): Maybe<List<Property>> {

        val local = propertyLocalDataSource.findByCity(city)
                .filter { !it.isEmpty() }

        val remote = propertyRemoteDataSource.findByCity(city)
                .toMaybe()
                .doOnSuccess {
                    val cityLocal = cityMapper.toLocalModel(it.location.city)
                    cityLocalDataSource.insert(cityLocal)
                }
                .map { propertyMapper.toLocalModel(city, it.properties) }
                .doOnSuccess(propertyLocalDataSource::insertAll)

        val concat = Maybe.just(refresh)
                .doOnSuccess { if (it) propertyLocalDataSource.deleteByCity(city) }
                .flatMap {
                    Maybe.concat(local, remote)
                            .firstElement()
                }

        return concat.map(propertyMapper::toEntity)
                .subscribeOn(appSchedulers.io)
    }
}
