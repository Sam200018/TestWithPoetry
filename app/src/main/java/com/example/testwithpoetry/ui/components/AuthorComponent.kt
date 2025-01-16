package com.example.testwithpoetry.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testwithpoetry.localModels.Authors

@Composable
fun AuthorComponent(
    modifier: Modifier = Modifier,
    author: Authors,
    navToAuthorDetails: (String) -> Unit,
    onSave: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(onClick = {
            navToAuthorDetails(author.name)
    }) {

        ListItem(headlineContent = { Text(author.name) },
            modifier
                .padding(
                    horizontal = 8.dp, vertical = 0.dp
                )
                .fillMaxWidth(), trailingContent = {
                IconButton(onClick = {
                    if (author.isSaved) onDelete(author.name)
                    else onSave(author.name)

                }) {
                    if (author.isSaved) Icon(
                        Icons.Default.Star,
                        "",
                        modifier.fillMaxSize(),
                        tint = Color(0xFFFFDA03)
                    )
                    else Icon(Icons.Outlined.StarOutline, "", modifier.fillMaxSize())
                }
            })
    }


}

