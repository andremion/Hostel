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

package com.andremion.hostel.app.internal.injection.module

import android.content.Context
import com.andremion.hostel.data.BuildConfig
import com.andremion.hostel.data.local.CityLocalDataSource
import com.andremion.hostel.data.local.PropertyLocalDataSource
import com.andremion.hostel.data.local.dao.CityDao
import com.andremion.hostel.data.local.dao.PropertyDao
import com.andremion.hostel.data.local.database.LocalDatabase
import com.andremion.hostel.data.local.database.disk.DiskDatabase
import com.andremion.hostel.data.remote.PropertyRemoteDataSource
import com.andremion.hostel.data.remote.api.HostelApi
import com.andremion.hostel.data.remote.api.HostelService
import com.andremion.hostel.data.repository.HostelRepository
import com.andremion.hostel.data.repository.mapper.CityMapper
import com.andremion.hostel.data.repository.mapper.PropertyMapper
import com.andremion.hostel.scheduler.AppSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DataModule {

    @Provides
    @Singleton
    internal fun provideHostelService(): HostelService = HostelApi(BuildConfig.API_URL)

    @Provides
    @Singleton
    internal fun providePropertyRemoteDataSource(hostelService: HostelService): PropertyRemoteDataSource {
        return PropertyRemoteDataSource(hostelService)
    }

    @Provides
    @Singleton
    internal fun provideLocalDatabase(context: Context): LocalDatabase {
        return DiskDatabase.newInstance(context)
    }

    @Provides
    @Singleton
    internal fun provideCityDao(localDatabase: LocalDatabase): CityDao = localDatabase.cityDao()

    @Provides
    @Singleton
    internal fun providePropertyDao(localDatabase: LocalDatabase): PropertyDao = localDatabase.propertyDao()

    @Provides
    @Singleton
    internal fun provideCityLocalDataSource(cityDao: CityDao): CityLocalDataSource {
        return CityLocalDataSource(cityDao)
    }

    @Provides
    @Singleton
    internal fun providePropertyLocalDataSource(propertyDao: PropertyDao): PropertyLocalDataSource {
        return PropertyLocalDataSource(propertyDao)
    }

    @Provides
    @Singleton
    internal fun providePropertyRepository(appSchedulers: AppSchedulers,
                                           cityLocalDataSource: CityLocalDataSource,
                                           propertyLocalDataSource: PropertyLocalDataSource,
                                           propertyRemoteDataSource: PropertyRemoteDataSource): HostelRepository {
        return HostelRepository(appSchedulers, cityLocalDataSource, propertyLocalDataSource, propertyRemoteDataSource,
                CityMapper(), PropertyMapper())
    }

}
