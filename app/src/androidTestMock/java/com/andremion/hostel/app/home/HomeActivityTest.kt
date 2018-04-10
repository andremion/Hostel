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

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.andremion.hostel.R
import com.andremion.hostel.app.internal.test.DaggerTestRule
import com.andremion.hostel.app.internal.test.withTitle
import com.andremion.hostel.app.property.list.adapter.PropertyListAdapter
import com.andremion.hostel.data.PropertiesByCityJson
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val testRule = DaggerTestRule()

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<*> = ActivityTestRule<HomeActivity>(HomeActivity::class.java, true, false)

    @Test
    fun shouldShowPropertiesByCitySortedByFeaturedDescendingAndRatingDescending() {

        // Given
        val response = MockResponse().setBody(PropertiesByCityJson.getJson())
        val propertiesByCity = PropertiesByCityJson.getObjects()
        val properties = propertiesByCity.properties
                // For now, just test the first three items as we have an issue
                // scrolling RecyclerView inside CollapsingToolbarLayout with Espresso
                .subList(0, 1)

        // When
        testRule.server.enqueue(response)
        activityRule.launchActivity(Intent())

        properties.forEachIndexed { position, property ->
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

        // City assertion
        onView(withTitle(propertiesByCity.location.city.name))
                .check(matches(isDisplayed()))
    }

    @Test
    fun whenGetAnError_ShouldShowASnackbar() {

        // Given
        val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_GATEWAY)

        // When
        testRule.server.enqueue(response)
        activityRule.launchActivity(Intent())

        // Then
        onView(allOf(withId(R.id.snackbar_text), withText(containsString("HTTP 502"))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun whenGetAnEmptyList_ShouldShowAnyInfo() {

        // Given
        val response = MockResponse().setBody(PropertiesByCityJson.getEmptyJson())

        // When
        testRule.server.enqueue(response)
        activityRule.launchActivity(Intent())

        // Then
        onView(withId(R.id.view_empty))
                .check(matches(isDisplayed()))
        onView(withText(R.string.empty_icon))
                .check(matches(isDisplayed()))
        onView(withText(R.string.property_list_empty))
                .check(matches(isDisplayed()))
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