package com.example.testwithpoetry.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingDialogComponent(modifier: Modifier = Modifier) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }, text = {
        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            CircularProgressIndicator()
        }
    })
}