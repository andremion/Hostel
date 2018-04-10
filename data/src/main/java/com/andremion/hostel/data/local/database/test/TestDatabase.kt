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

package com.andremion.hostel.data.local.database.test

import android.arch.persistence.room.Room
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.andremion.hostel.data.local.database.LocalDatabase

/**
 * Class used just for testing.
 * It is created here because Room doesn't resolve this well while running instrumentation tests.
 */
@VisibleForTesting
object TestDatabase {

    fun newInstance(context: Context): LocalDatabase {
        return Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }
}