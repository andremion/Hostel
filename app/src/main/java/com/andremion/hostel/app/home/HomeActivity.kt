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

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.andremion.hostel.R
import com.andremion.hostel.app.internal.util.lazyThreadSafetyNone
import com.andremion.hostel.app.property.list.PropertyListFragment
import com.andremion.hostel.app.property.list.PropertyListViewModel
import com.andremion.hostel.databinding.ActivityHomeBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    companion object {
        // FIXME: Temporary city
        const val CITY = 3
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binder by lazyThreadSafetyNone<ActivityHomeBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    private val cityViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(CityViewModel::class.java)
    }

    private val propertyListViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(PropertyListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binder.toolbar)
        binder.cityViewModel = cityViewModel
        binder.propertyListViewModel = propertyListViewModel

        val city = CITY
        cityViewModel.loadCity(city)

        if (supportFragmentManager.findFragmentById(R.id.content_layout) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.content_layout, PropertyListFragment.newInstance(city))
                    .commit()
        }
    }
}
