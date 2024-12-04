package com.siva.data.mapper

import com.siva.data.model.HotelSearchResultResponse
import com.siva.data.model.HotelsLocation
import com.siva.domain.model.Hotel
import com.siva.domain.model.HotelGeoLocation
import javax.inject.Inject

class HotelSearchResponseMapper @Inject constructor() {

    fun transform(hotelSearchResponse: HotelSearchResultResponse): List<Hotel>{
        val hotelList = mutableListOf<Hotel>()
        hotelSearchResponse.results.hotels.forEach { hotelResp ->
            val hotel = Hotel(
                hotelId = hotelResp.id,
                hotelName = hotelResp.label,
                locationId = hotelResp.locationId,
                locationName = hotelResp.locationName,
                address = hotelResp.fullName,
                hotelScorePoint = hotelResp._score.toString(),
                geoLocation = getGeoLatLong(hotelResp.location),
                contactNumber = getRandomMobileNumber()
            )
            hotelList.add(hotel)
        }
        return hotelList
    }

    private fun getGeoLatLong(location: HotelsLocation): HotelGeoLocation {
        return HotelGeoLocation(
            lat = location.lat,
            long = location.lon
        )
    }

    private fun getRandomMobileNumber(): String {
        val random1 = (0..10).random()
        val random2 = (0..10).random()
        return "93848$random1" + "497$random2"
    }
}