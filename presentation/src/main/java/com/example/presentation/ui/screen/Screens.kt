package com.example.presentation.ui.screen

sealed class Screens(
    val route: String,
    val screenName: String
) {
    object HotelList : Screens(
        route = "hotel_list",
        screenName = "HotelList"
    )

    object HotelDetails : Screens(
        route = "hotel_details",
        screenName = "Hotel Details"
    )
}