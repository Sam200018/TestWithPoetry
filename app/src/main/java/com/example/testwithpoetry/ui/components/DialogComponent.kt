package com.example.testwithpoetry.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DialogComponent(
    modifier: Modifier = Modifier,
    title: String,
    message: List<String>,
    onDismissRequest: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {

            TextButton(onClick = onDismissRequest) {
                Text(text = "Close")
            }
        },
        title = {
            Text(title)
        },
        text = {
            val scrollState = rememberScrollState()
            Column(modifier.verticalScroll(scrollState)) {
                message.forEach { line ->
                    Text(line)
                }
            }
        },
    )
}
