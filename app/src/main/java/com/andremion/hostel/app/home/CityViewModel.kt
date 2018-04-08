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

package com.andremion.hostel.app.home

import android.databinding.ObservableField
import com.andremion.hostel.app.internal.DisposableViewModel
import com.andremion.hostel.data.entity.City
import com.andremion.hostel.data.repository.HostelRepository
import com.andremion.hostel.scheduler.AppSchedulers

class CityViewModel(
        private val appSchedulers: AppSchedulers,
        private val hostelRepository: HostelRepository) : DisposableViewModel() {

    val data = ObservableField<City>()

    fun loadCity(city: Int) {
        addDisposable(hostelRepository.getById(city)
                .observeOn(appSchedulers.main)
                .subscribe(data::set))
    }
}
