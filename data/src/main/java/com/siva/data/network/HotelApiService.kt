package com.siva.data.network

import com.example.core.Constants.Companion.SEARCH_ENDPOINT
import com.siva.data.model.HotelSearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelApiService {
    @GET(SEARCH_ENDPOINT)
    suspend fun searchHotels(
        @Query("query") query: String,
        @Query("lang") lang: String = "en",
        @Query("lookFor") lookFor: String = "hotel",
        @Query("limit") limit: Int = 20
    ): HotelSearchResultResponse
}