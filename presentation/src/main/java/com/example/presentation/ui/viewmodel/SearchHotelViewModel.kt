package com.example.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.RequestState
import com.example.presentation.ui.model.HotelSearchUiState
import com.siva.domain.model.Hotel
import com.siva.domain.usecase.SearchHotelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHotelViewModel @Inject constructor(
    private val searchHotelUseCase: SearchHotelUseCase
) : ViewModel() {

    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    val hotels: StateFlow<List<Hotel>> = _hotels

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchInputField = MutableStateFlow("")
    val searchInputField:StateFlow<String> = _searchInputField

    private val _uiState = MutableStateFlow<HotelSearchUiState>(HotelSearchUiState.Loading)
    val uiState: StateFlow<HotelSearchUiState> = _uiState

    init {
        searchHotels(searchString = "")
    }

    /*fun searchHotels(searchString: String) {
        viewModelScope.launch {
            _isLoading.value = true
            searchHotelUseCase.getSearchHotelList(searchString).collect {
                _hotels.value = it
                _isLoading.value = false
            }
        }
    }*/

    fun searchHotels(searchString: String) {
        _uiState.value = HotelSearchUiState.Loading
        viewModelScope.launch {
            searchHotelUseCase.getSearchHotelList(searchString).collect { result ->
                when (result) {
                    is RequestState.SuccessState -> {
                        _uiState.value = HotelSearchUiState.Success(hotels = result.data)
                    }

                    is RequestState.FailureState -> {
                        _uiState.value = HotelSearchUiState.Error(message = result.error.toString())
                    }
                }
            }
        }
    }

    fun onSearchTextChange(searchLocation: String) {
        _searchInputField.value = searchLocation
    }

    fun getSearchText() = _searchInputField.value

    fun clearInput() {
        _uiState.value = HotelSearchUiState.Loading
        _searchInputField.update { "" }
        _uiState.value = HotelSearchUiState.Success(hotels = emptyList())
    }
}