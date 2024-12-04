package com.example.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.presentation.ui.viewmodel.SearchHotelViewModel
import com.siva.domain.model.Hotel
import com.siva.domain.model.HotelGeoLocation
import com.siva.domain.repository.SearchHotelRepository
import com.siva.domain.usecase.SearchHotelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchHotelViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var searchHotelRepository: SearchHotelRepository

    @Mock
    private lateinit var searchHotelUseCase: SearchHotelUseCase

    private lateinit var searchHotelViewModel: SearchHotelViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        searchHotelViewModel = SearchHotelViewModel(searchHotelUseCase)

        val mockHotels = listOf(
            Hotel(
                hotelId = "602050",
                hotelName = "Ginger Chennai - Vadapalani",
                locationId = 25864,
                locationName = "Chennai India",
                address = "Vadapalani",
                hotelScorePoint = "101994",
                geoLocation = HotelGeoLocation(lat = 13.07719, long = 80.26118)
            ),
            Hotel(
                hotelId = "602051",
                hotelName = "Fabhotel Prime Chennai Deluxe",
                locationId = 25865,
                locationName = "Chennai India",
                address = "Anna Nagar, Chennai",
                hotelScorePoint = "101994",
                geoLocation = HotelGeoLocation(lat = 13.06645, long = 80.20944)
            )
        )

        Mockito.`when`(searchHotelRepository.getHotelList("chennai")).thenReturn(flow { emit(mockHotels) })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetHotels() = testScope.runTest {
        val job = launch {
            searchHotelViewModel.hotels.collect { hotels ->
                Assert.assertEquals(2, hotels.size)
                Assert.assertEquals("Ginger Chennai - Vadapalani", hotels[0].hotelName)
                Assert.assertEquals("Fabhotel Prime Chennai Deluxe", hotels[1].hotelName)
            }
        }
        job.cancel()
    }

    @Test
    fun testLoadingState() = testScope.runTest {
        val loadingStates = mutableListOf<Boolean>()
        val job = launch {
            searchHotelViewModel.isLoading.collect { isLoading ->
                loadingStates.add(isLoading)
            }
        }

        // Allow some time for loading states to be updated
        advanceUntilIdle()

        // Initially, loading should be true, then false after loading
        Assert.assertEquals(listOf(false), loadingStates)

        job.cancel()
    }

}