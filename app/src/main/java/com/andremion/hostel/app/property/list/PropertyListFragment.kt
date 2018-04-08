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

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andremion.hostel.R
import com.andremion.hostel.app.internal.util.lazyThreadSafetyNone
import com.andremion.hostel.databinding.FragmentPropertyListBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PropertyListFragment : DaggerFragment() {

    companion object {

        private const val ARG_CITY = "city"

        fun newInstance(type: Int): PropertyListFragment {
            val fragment = PropertyListFragment()
            val args = Bundle()
            args.putInt(ARG_CITY, type)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binder: FragmentPropertyListBinding

    private val viewModel by lazyThreadSafetyNone {
        activity?.let { ViewModelProviders.of(it, viewModelFactory).get(PropertyListViewModel::class.java) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_property_list, container, false)
        binder.viewModel = viewModel
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val eventType = arguments?.getInt(ARG_CITY)!!
        viewModel?.loadPropertyList(eventType)
    }

}