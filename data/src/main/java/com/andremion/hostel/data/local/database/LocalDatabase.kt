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

package com.andremion.hostel.data.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.andremion.hostel.data.local.dao.CityDao
import com.andremion.hostel.data.local.dao.PropertyDao
import com.andremion.hostel.data.local.model.CityLocal
import com.andremion.hostel.data.local.model.PropertyLocal

@Database(entities = [CityLocal::class, PropertyLocal::class], version = 1, exportSchema = false)
@TypeConverters(DataTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun propertyDao(): PropertyDao
}