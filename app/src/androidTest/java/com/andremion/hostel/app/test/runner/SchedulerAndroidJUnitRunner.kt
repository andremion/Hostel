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

package com.andremion.hostel.app.test.runner

import android.os.AsyncTask
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class SchedulerAndroidJUnitRunner : AndroidJUnitRunner() {

    companion object {
        val javaScheduler = Function<Scheduler, Scheduler> { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
    }

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        setupSchedulers()
    }

    override fun onDestroy() {
        releaseSchedulers()
        super.onDestroy()
    }

    /**
     * Setup Rx [Schedulers] to use [AsyncTask] executor.
     * Espresso is aware of AsyncTasks, and will wait for them to complete before proceeding.
     */
    private fun setupSchedulers() {
        RxJavaPlugins.setComputationSchedulerHandler(javaScheduler)
        RxJavaPlugins.setIoSchedulerHandler(javaScheduler)
        RxJavaPlugins.setNewThreadSchedulerHandler(javaScheduler)
        RxJavaPlugins.setSingleSchedulerHandler(javaScheduler)
    }

    private fun releaseSchedulers() {
        RxJavaPlugins.reset()
    }
}
