package com.siva.data.repository

import android.util.Log
import com.example.core.RequestState
import com.siva.data.mapper.HotelSearchResponseMapper
import com.siva.data.network.HotelApiService
import com.siva.domain.model.Hotel
import com.siva.domain.repository.SearchHotelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchHotelRepoImpl @Inject constructor(
    private val apiService: HotelApiService,
    private val mapper: HotelSearchResponseMapper
) : SearchHotelRepository {

    /*override fun getHotelList(searchQuery: String): Flow<List<Hotel>> = flow {
        try {
            val response = apiService.searchHotels(searchQuery)
            if (response.status == "ok") {
                Log.i("TAG", "getHotelSearch: --> Success}")
                emit(mapper.transform(response))  // mapping into response model
            } else {
                emit(emptyList())
            }
        } catch (exception: Exception) {
            Log.i("TAG", "getHotelSearch: --> ${exception.message}")
            emit(emptyList())
        }

    }*/

    override fun getHotelList(searchQuery: String) : Flow<RequestState<List<Hotel>>> = flow {
        try {
            val response = apiService.searchHotels(searchQuery)
            if (response.status == "ok") {
                Log.i("TAG", "getHotelSearch: --> Success}")
                emit(RequestState.SuccessState(data = mapper.transform(response)))
                // emit(mapper.transform(response))  // mapping into response model
            } else {
                emit(RequestState.FailureState(error = Throwable("Unknown Exception")))
                // emit(emptyList())
            }
        } catch (exception: Exception) {
            Log.i("TAG", "getHotelSearch: --> ${exception.message}")
            //  emit(emptyList())
            emit(RequestState.FailureState(error = Throwable("Something went wrong")))
        }
    }

}