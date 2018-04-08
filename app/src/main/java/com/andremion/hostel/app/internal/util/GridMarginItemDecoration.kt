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

package com.andremion.hostel.app.internal.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridMarginItemDecoration(private val margin: Int,
                               private val spanCount: Int = 1,
                               private val includesEdge: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val position = parent.getChildAdapterPosition(view)

        if (position >= 0) {
            val column = position % spanCount

            if (includesEdge) {

                outRect.left = margin - column * margin / spanCount
                if (position < spanCount) {
                    outRect.top = margin
                }
                outRect.right = (column + 1) * margin / spanCount
                outRect.bottom = margin

            } else {

                outRect.left = column * margin / spanCount
                if (position >= spanCount) {
                    outRect.top = margin
                }
                outRect.right = margin - (column + 1) * margin / spanCount

            }
        }

    }

}