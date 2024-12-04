package com.siva.domain.usecase

import com.example.core.RequestState
import com.siva.domain.model.Hotel
import com.siva.domain.repository.SearchHotelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHotelUseCase @Inject constructor(
    private val searchHotelRepository: SearchHotelRepository
) {
   /* fun getSearchHotelList(search: String): Flow<List<Hotel>> {
        return searchHotelRepository.getHotelList( searchQuery = search)
    }*/

    fun getSearchHotelList(search: String): Flow<RequestState<List<Hotel>>> {
        return searchHotelRepository.getHotelList( searchQuery = search)
    }
}