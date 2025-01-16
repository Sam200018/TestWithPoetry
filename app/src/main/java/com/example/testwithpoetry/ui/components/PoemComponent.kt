package com.example.testwithpoetry.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PoemComponent(
    modifier: Modifier = Modifier,
    poemTitle: String,
    onSelectPoem: (String) -> Unit
) {
    OutlinedCard(onClick = {
        onSelectPoem(poemTitle)
    }) {
        ListItem(
            headlineContent = { Text(poemTitle) },
            modifier
                .padding(
                    horizontal = 8.dp, vertical = 0.dp
                )
                .fillMaxWidth(),
        )
    }
}