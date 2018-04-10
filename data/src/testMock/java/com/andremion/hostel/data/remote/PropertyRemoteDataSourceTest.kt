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

package com.andremion.hostel.data.remote

import com.andremion.hostel.data.PropertiesByCityJson
import com.andremion.hostel.data.remote.api.HostelApi
import com.andremion.hostel.data.remote.api.HostelService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("RemoveRedundantBackticks")
@RunWith(JUnit4::class)
class PropertyRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var hostelService: HostelService

    private lateinit var propertyRemoteDataSource: PropertyRemoteDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        hostelService = HostelApi(mockWebServer.url("/").toString())

        propertyRemoteDataSource = PropertyRemoteDataSource(hostelService)
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun `Given properties json data, When we call find properties by city, Should get those properties`() {

        // Given

        val city = 1L
        val propertiesByCity = PropertiesByCityJson.getObjects()
        val response = MockResponse().setBody(PropertiesByCityJson.getJson())
        mockWebServer.enqueue(response)

        // When

        val testObserver = propertyRemoteDataSource.findByCity(city)
                .test()

        // Then

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(propertiesByCity)
    }

}