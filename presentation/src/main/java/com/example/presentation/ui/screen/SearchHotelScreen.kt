package com.example.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.Constants.Companion.HOTEL_ID_KEY
import com.example.presentation.ui.model.HotelSearchUiState
import com.example.presentation.ui.utils.AppBar
import com.example.presentation.ui.viewmodel.SearchHotelViewModel
import com.siva.domain.model.Hotel

@Composable
fun SearchHotelsScreen(viewModel: SearchHotelViewModel = hiltViewModel()) {

    val hotels by viewModel.hotels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchInputField.collectAsState()

    val state by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        when(state) {
            is HotelSearchUiState.Loading -> {
                LoadingProgressView()
            }
            is HotelSearchUiState.Success -> {
                HotelNavigation(hotels = (state as HotelSearchUiState.Success).hotels, searchViewModel = viewModel, searchText = searchText)
            }
            is HotelSearchUiState.Error -> {
                ErrorScreen((state as HotelSearchUiState.Error).message)
            }
        }

       /* if (isLoading && hotels.isEmpty()) {
            LoadingProgressView()
        } else {
            HotelNavigation(hotels = hotels, searchViewModel = viewModel, searchText = searchText)
        }*/
    }

}

@Composable
fun HotelNavigation(
    hotels: List<Hotel>,
    searchViewModel: SearchHotelViewModel,
    searchText: String,
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HotelList.route) {

        composable(Screens.HotelList.route) {
            SearchHotelListScreen(
                navController = navController,
                hotels = hotels,
                searchText = searchText,
                viewModel = searchViewModel
            )
        }

        composable(route = "${Screens.HotelDetails.route}/{$HOTEL_ID_KEY}") {
            val hotelId = it.arguments?.getString(HOTEL_ID_KEY)
            val hotel = hotels.first { hotelItem ->
                hotelItem.hotelId == hotelId
            }
            HotelDetailsScreen(hotel = hotel)
        }
    }

}

@Composable
fun SearchHotelListScreen(
    navController: NavController,
    viewModel: SearchHotelViewModel,
    searchText: String,
    hotels: List<Hotel>
) {
    Column {
        AppBar(title = "Hotels Search")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(16.dp)
        ) {
            SearchView(viewModel, searchText)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                userScrollEnabled = true,
            ) {
                items(hotels) { hotel ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 5.dp),
                        onClick = {
                            navController.navigate(
                                route = "${Screens.HotelDetails.route}/${hotel.hotelId}"
                            )
                        }) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f, fill = false),
                                text = hotel.hotelName,
                                maxLines = 1,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchView(
    viewModel: SearchHotelViewModel,
    searchString: String
) {

    OutlinedTextField(
        value = searchString,
        onValueChange = viewModel::onSearchTextChange,
        placeholder = { Text(text = "Enter Location") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (viewModel.getSearchText().isEmpty()) {
                speechIconView
            } else {
                ClearInputField(viewModel)
            }
        },
        singleLine = true,
        shape = CircleShape,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { viewModel.searchHotels(searchString) },
        )
    )
}

val speechIconView = @Composable {
    LocalContext.current
    IconButton(
        onClick = { },
    ) {
        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "",
            tint = Color.Black
        )
    }
}

@Composable
fun ClearInputField(viewModel: SearchHotelViewModel) {
    IconButton(
        onClick = { viewModel.clearInput() },
    ) {
        Icon(
            Icons.Default.Clear,
            contentDescription = "",
            tint = Color.Black
        )
    }
}

@Composable
fun LoadingProgressView() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
