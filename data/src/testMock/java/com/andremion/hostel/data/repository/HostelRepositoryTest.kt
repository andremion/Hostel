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

import com.andremion.hostel.data.PropertiesMock
import com.andremion.hostel.data.local.CityLocalDataSource
import com.andremion.hostel.data.local.PropertyLocalDataSource
import com.andremion.hostel.data.remote.PropertyRemoteDataSource
import com.andremion.hostel.data.repository.mapper.CityMapper
import com.andremion.hostel.data.repository.mapper.PropertyMapper
import com.andremion.hostel.scheduler.TestAppSchedulers
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@Suppress("RemoveRedundantBackticks")
@RunWith(MockitoJUnitRunner::class)
class HostelRepositoryTest {

    @Mock
    private lateinit var cityLocalDataSource: CityLocalDataSource
    @Mock
    private lateinit var propertyLocalDataSource: PropertyLocalDataSource
    @Mock
    private lateinit var propertyRemoteDataSource: PropertyRemoteDataSource

    private lateinit var hostelRepository: HostelRepository

    @Before
    fun setUp() {
        hostelRepository = HostelRepository(TestAppSchedulers(),
                cityLocalDataSource, propertyLocalDataSource, propertyRemoteDataSource,
                CityMapper(), PropertyMapper())
    }

    @Test
    @Throws(Exception::class)
    fun `Given local data, When find properties with refresh flag, Should delete local data, fetch remote data then insert them locally`() {

        // Given

        val refresh = true
        val city = PropertiesMock.getLocalCity(1)
        val localProperties = PropertiesMock.getLocalProperties(city.id)
        val propertiesByCity = PropertiesMock.getPropertiesByCity(city.id)
        val properties = PropertiesMock.getProperties(city.id)
        // Empty local data is expected due to deletion of local source
        `when`(propertyLocalDataSource.findByCity(city.id)).thenReturn(Maybe.empty())
        // Fetch remote data
        `when`(propertyRemoteDataSource.findByCity(city.id)).thenReturn(Single.just(propertiesByCity))

        // When

        val testObserver = hostelRepository.findByCity(city.id, refresh)
                .test()

        // Then

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(properties)

        val inOrder = inOrder(propertyLocalDataSource, cityLocalDataSource)
        // Delete local source by type
        inOrder.verify(propertyLocalDataSource).deleteByCity(city.id)
        // Call both data sources
        verify(propertyLocalDataSource).findByCity(city.id)
        verify(propertyRemoteDataSource).findByCity(city.id)
        // Insert into local source
        inOrder.verify(cityLocalDataSource).insert(city)
        inOrder.verify(propertyLocalDataSource).insertAll(localProperties)
    }

}