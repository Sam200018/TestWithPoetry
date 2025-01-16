package com.example.testwithpoetry.ui.main.interactions

import com.example.testwithpoetry.localModels.Authors
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleReponse


data class AuthorsState(
    val authorsList: List<Authors> = listOf(),
    val selectedAuthor: String? = null,
    val poemsList: List<PoemTitleReponse> = listOf(),
    val poemSelected: PoemResponse? = null,
    val message: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
)
