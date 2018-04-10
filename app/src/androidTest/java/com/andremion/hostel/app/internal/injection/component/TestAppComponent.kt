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

package com.andremion.hostel.app.internal.injection.component

import android.app.Application
import com.andremion.hostel.app.internal.injection.DaggerApplication
import com.andremion.hostel.app.internal.injection.module.ActivitiesModule
import com.andremion.hostel.app.internal.injection.module.TestAppModule
import com.andremion.hostel.app.internal.injection.module.TestDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main dagger component for instrumentation testing.
 * [TestAppModule] to setup Schedulers and [TestDataModule] to setup testing data sources.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    TestAppModule::class,
    TestDataModule::class])
internal interface TestAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun testDataModule(testDataModule: TestDataModule): Builder

        fun build(): TestAppComponent
    }

    fun inject(daggerApplication: DaggerApplication)

}
