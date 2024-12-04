package com.example.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.siva.domain.model.Hotel
import com.siva.domain.model.HotelGeoLocation
import com.siva.domain.repository.SearchHotelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SearchHotelScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockHotels = listOf(
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

    @Mock
    private lateinit var searchHotelRepository: SearchHotelRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setupMockRepository()
    }

    private fun setupMockRepository() {
        val flow = flow { emit(mockHotels) }
        whenever(searchHotelRepository.getHotelList("chennai")).thenReturn(flow)
    }

    @Test
    fun hotelSearchScreen_loadingState_displaysLoadingIndicator() {
        val isLoadingFlow = MutableStateFlow(true)
        val hotelsFlow = MutableStateFlow(emptyList<Hotel>())
    }

    @Test
    fun hotelSearchScreen_hotelsLoaded_displaysFixtures() {
        val isLoadingFlow = MutableStateFlow(false)
        val hotelsFlow = MutableStateFlow(mockHotels)

        composeTestRule.onNodeWithText("Ginger Chennai - Vadapalani").assertExists()
        composeTestRule.onNodeWithText("Fabhotel Prime Chennai Deluxe").assertExists()
    }

}