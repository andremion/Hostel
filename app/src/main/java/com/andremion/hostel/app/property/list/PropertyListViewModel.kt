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

package com.andremion.hostel.app.property.list

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.andremion.hostel.app.internal.DisposableViewModel
import com.andremion.hostel.data.entity.Property
import com.andremion.hostel.data.repository.HostelRepository
import com.andremion.hostel.scheduler.AppSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableMaybeObserver

class PropertyListViewModel(
        private val appSchedulers: AppSchedulers,
        private val hostelRepository: HostelRepository) : DisposableViewModel() {

    val loading = ObservableBoolean()
    val data = ObservableArrayList<Property>()
    val empty = ObservableBoolean()
    val error = ObservableField<String>()

    private var city = 0L

    fun loadPropertyList(city: Long, refresh: Boolean = false) {
        this.city = city
        addDisposable(getAll(city, refresh))
    }

    fun refresh() = loadPropertyList(city, true)

    private fun getAll(city: Long, refresh: Boolean): Disposable {
        return hostelRepository.findByCity(city, refresh)
                .observeOn(appSchedulers.main)
                .subscribeWith(object : DisposableMaybeObserver<List<Property>>() {

                    override fun onStart() {
                        loading.set(true)
                    }

                    override fun onSuccess(t: List<Property>) {
                        loading.set(false)
                        data.clear()
                        data.addAll(t)
                        empty.set(t.isEmpty())
                    }

                    override fun onError(t: Throwable) {
                        loading.set(false)
                        error.set(t.message)
                    }

                    override fun onComplete() {
                        // no-op
                    }
                })
    }
}
