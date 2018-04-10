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

package com.andremion.hostel.app.internal.test

import android.support.design.widget.CollapsingToolbarLayout
import android.support.test.espresso.core.internal.deps.guava.base.Preconditions
import android.support.test.espresso.matcher.BoundedMatcher
import android.util.Log
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`

fun withTitle(title: String) = withTitle(`is`(title))

/**
 * Returns a matcher that matches [CollapsingToolbarLayout]s based on title property value.
 * Note: [CollapsingToolbarLayout]'s title property can be null.
 */
fun withTitle(stringMatcher: Matcher<String>): Matcher<View> {
    Preconditions.checkNotNull(stringMatcher)
    return object : BoundedMatcher<View, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with title: ")
            stringMatcher.describeTo(description)
        }

        public override fun matchesSafely(collapsingToolbarLayout: CollapsingToolbarLayout): Boolean {
            Log.d("alor", collapsingToolbarLayout.title.toString())
            return stringMatcher.matches(collapsingToolbarLayout.title)
        }
    }
}