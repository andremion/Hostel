package com.andremion.hostel.data.local

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.andremion.hostel.data.PropertiesMock
import com.andremion.hostel.data.local.database.LocalDatabase
import com.andremion.hostel.data.local.database.test.TestDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyLocalDataSourceTest {

    private lateinit var database: LocalDatabase
    private lateinit var propertyLocalDataSource: PropertyLocalDataSource

    @Before
    fun setUp() {
        database = TestDatabase.newInstance(InstrumentationRegistry.getContext())
        propertyLocalDataSource = PropertyLocalDataSource(database.propertyDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun whenInsertCityPropertiesOnLocalDataSource_ThosePropertiesShouldBeFoundByThatCitySortedByFeaturedAndByRatingDescending() {

        // Given

        val city = 1L
        val properties = PropertiesMock.getLocalProperties(city)

        // When

        propertyLocalDataSource.insertAll(properties)

        val testObserver = propertyLocalDataSource.findByCity(city)
                .test()

        // Then

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(properties)
    }

    @Test
    fun whenPropertiesAreDeletedByCity_ShouldBeNotFoundByThatCity() {

        // Given

        val city = 1L
        val properties = PropertiesMock.getLocalProperties(city)
        propertyLocalDataSource.insertAll(properties)

        // When

        propertyLocalDataSource.deleteByCity(city)

        val testObserver = propertyLocalDataSource.findByCity(city)
                .test()

        // Then

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(listOf())
    }

}