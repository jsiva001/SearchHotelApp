package com.siva.domain.model

data class Hotel(
    val hotelId: String,
    val hotelName: String,
    val locationId: Int,
    val locationName: String,
    val address: String,
    val hotelScorePoint:String,
    val geoLocation: HotelGeoLocation,
    val contactNumber: String = "",
)

data class HotelGeoLocation(
    val lat: Double,
    val long: Double,
)