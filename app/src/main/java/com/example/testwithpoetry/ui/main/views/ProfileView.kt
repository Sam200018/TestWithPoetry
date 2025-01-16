package com.example.testwithpoetry.ui.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    birthday: Long
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Name: $name")
        Spacer(modifier.height(20.dp))
        Text("Email: $email")
        Spacer(modifier.height(20.dp))
        Text("Birthday: ${convertMillisToDate(birthday)}")

    }

}
