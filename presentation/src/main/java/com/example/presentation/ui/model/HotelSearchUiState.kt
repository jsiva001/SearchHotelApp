package com.example.presentation.ui.model

import com.siva.domain.model.Hotel

sealed class HotelSearchUiState {
    data object Loading : HotelSearchUiState()
    data class Success(val hotels: List<Hotel>) : HotelSearchUiState()
    data class Error(val message: String) : HotelSearchUiState()
}