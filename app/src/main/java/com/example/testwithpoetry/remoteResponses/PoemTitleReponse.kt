package com.example.testwithpoetry.remoteResponses

import kotlinx.serialization.Serializable

@Serializable
data class PoemTitleReponse(
    val title: String
)