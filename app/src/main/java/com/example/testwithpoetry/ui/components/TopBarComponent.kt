package com.example.testwithpoetry.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {},
    backAction: (() -> Unit)? = null
) {
    TopAppBar(

        navigationIcon = {
            if (backAction != null)
                IconButton(onClick = backAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                    )
                }
        },
        title = { Text(text = title) },
        actions = actions,
    )
}
