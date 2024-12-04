package com.example.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.ui.utils.AppBar
import com.siva.domain.model.Hotel

@Composable
fun HotelDetailsScreen(hotel: Hotel) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                AppBar(title = "Hotel Details")
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = hotel.hotelName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }

                TitleAndDescText(text = "Address")

                TitleAndDescText(hotel.address)

                TitleAndDescText(text = "Location")

                TitleAndDescText(hotel.locationName)

                TitleAndDescText(text = "Contact Number")

                TitleAndDescText(hotel.contactNumber)
            }
        }
    }
}

@Composable
fun TitleAndDescText(text: String, isDescription: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            fontSize = if (isDescription) 14.sp else 16.sp,
            fontWeight = if (isDescription) FontWeight.Light else FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}