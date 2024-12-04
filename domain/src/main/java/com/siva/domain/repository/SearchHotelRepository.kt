package com.siva.domain.repository

import com.example.core.RequestState
import com.siva.domain.model.Hotel
import kotlinx.coroutines.flow.Flow

interface SearchHotelRepository {
  //  fun getHotelList( searchQuery: String = ""): Flow<List<Hotel>>
   fun getHotelList( searchQuery: String = ""): Flow<RequestState<List<Hotel>>>
}