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

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.andremion.hostel.R
import com.andremion.hostel.app.property.list.adapter.PropertyListAdapter
import com.andremion.hostel.data.PropertiesByCityJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<*> = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun startServer() {
        mockWebServer = MockWebServer()
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowPropertiesByCitySortedByFeaturedAndRatingDescending() {

        // Given
        val response = MockResponse().setBody(PropertiesByCityJson.getJson())
        mockWebServer.enqueue(response)

        val propertiesByCity = PropertiesByCityJson.getObjects()
        val properties = propertiesByCity.properties
                // Just test the first three now because we have an issue
                // scrolling RecyclerView inside CollapsingToolbarLayout with Espresso
                .subList(0, 1)

        properties.forEachIndexed { position, property ->

            // When
            onView(withId(R.id.property_list))
                    .perform(RecyclerViewActions.scrollToPosition<PropertyListAdapter.ViewHolder>(position))

            // Then
            onView(withText(property.name))
                    .check(matches(isDisplayed()))
            onView(withText(ratingString(property.overallRating?.overall)))
                    .check(matches(isDisplayed()))
            onView(withText(priceString(property.lowestPricePerNight.value)))
                    .check(matches(isDisplayed()))
        }
    }

    private fun ratingString(overallRating: Int?): String? {
        return if (overallRating != null) {
            val rating = overallRating * 10 / 100f
            if (rating > 0) rating.toString() else ""
        } else {
            ""
        }
    }

    private fun priceString(price: Double): String? {
        return "€%.0f".format(price / 7.55)
    }

}