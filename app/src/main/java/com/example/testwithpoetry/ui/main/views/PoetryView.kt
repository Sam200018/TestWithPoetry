package com.example.testwithpoetry.ui.main.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.testwithpoetry.localModels.Authors
import com.example.testwithpoetry.ui.components.AuthorComponent
import com.example.testwithpoetry.ui.main.viewmodels.AuthorsViewModel

@Composable
fun PoetryView(
    modifier: Modifier = Modifier,
    authorsList: List<Authors>,
    authorsViewModel: AuthorsViewModel
) {
    val context = LocalContext.current
    val state = rememberLazyListState()

    LazyColumn(state = state) {
        items(authorsList.size) {
            AuthorComponent(author = authorsList[it], onSave = { name ->
                authorsViewModel.saveAuthor(name) {
                    mToast(context)
                }
            }, onDelete = { name ->
                authorsViewModel.deleteAuthor(name)
            }, navToAuthorDetails = { name ->
                authorsViewModel.selectAuthor(name)
            })
        }
    }

}
