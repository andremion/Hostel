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

package com.andremion.hostel.data.remote.api

import com.andremion.hostel.data.remote.model.PropertiesByCity
import io.reactivex.Single
import retrofit2.http.GET

interface HostelService {

    @GET("dovdtel87/ef6dd1422a86554d22172e5975222f81/raw/ba5b81b567efebc1039a481b7e9712b7cd61ea6c/properties.json")
    fun findPropertiesByCity(): Single<PropertiesByCity>
}
