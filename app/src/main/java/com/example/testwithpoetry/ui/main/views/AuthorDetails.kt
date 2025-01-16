package com.example.testwithpoetry.ui.main.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleReponse
import com.example.testwithpoetry.ui.components.DialogComponent
import com.example.testwithpoetry.ui.components.PoemComponent

@Composable
fun AuthorDetails(
    modifier: Modifier = Modifier,
    poemList: List<PoemTitleReponse>,
    poemSelected: PoemResponse? = null,
    onSelectPoem: (String) -> Unit,
    onClosePoem: () -> Unit
) {


    LazyColumn {
        items(poemList.size) {
            PoemComponent(poemTitle = poemList.get(it).title, onSelectPoem = onSelectPoem)
        }
    }

    if (poemSelected != null) {
        DialogComponent(title = poemSelected.title, message = poemSelected.lines) {
            onClosePoem()
        }

    }

}