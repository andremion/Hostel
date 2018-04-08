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

package com.andremion.hostel.app.property.list.adapter

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.andremion.hostel.R
import com.andremion.hostel.app.internal.util.GridMarginItemDecoration
import com.andremion.hostel.data.entity.Property

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("propertyListAdapter")
    fun setReviewAdapter(recyclerView: RecyclerView, items: List<Property>?) {
        val adapter: PropertyListAdapter
        if (recyclerView.adapter == null) {

            setupRecyclerView(recyclerView)

            adapter = PropertyListAdapter()
            recyclerView.adapter = adapter
        } else {
            adapter = recyclerView.adapter as PropertyListAdapter
        }
        items?.let {
            adapter.submitList(
                    // Due to ListAdapter,
                    // this ensure the comparison occurs after the empty list have been set.
                    // So we call toList().
                    items.toList()
            )
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val margin = recyclerView.resources.getDimensionPixelOffset(R.dimen.text_margin)
        val numColumns = recyclerView.resources.getInteger(R.integer.grid_column_count)
        val itemDecoration = GridMarginItemDecoration(margin, numColumns, true)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, numColumns)
        recyclerView.setHasFixedSize(true)
    }
}
