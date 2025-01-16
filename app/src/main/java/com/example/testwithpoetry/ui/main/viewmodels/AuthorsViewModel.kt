package com.example.testwithpoetry.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.data.NetworkResource
import com.example.testwithpoetry.data.poetry.PoetryRepository
import com.example.testwithpoetry.localModels.Author
import com.example.testwithpoetry.localModels.Authors
import com.example.testwithpoetry.ui.main.interactions.AuthorsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorsViewModel @Inject constructor(
    private val poetryRepo: PoetryRepository,
) : ViewModel() {

    private val _authorsState = MutableStateFlow(AuthorsState())
    val authorsState = _authorsState.asStateFlow()


    init {
        _authorsState.update {
            it.copy(isLoading = true)
        }
        getAuthorsList()
    }

    private fun getAuthorsList() {
        viewModelScope.launch {
            val response = poetryRepo.getAuths()
            if (response is NetworkResource.Success) {
                val authors = response.data.authors.map { authorNet ->
                    val isAuthorSaved = poetryRepo.getAuthorByNameLocal(authorNet)
                    Authors(name = authorNet, isAuthorSaved.isSuccess)
                }

                _authorsState.update {
                    it.copy(
                        isLoading = false,
                        authorsList = authors
                    )
                }
            }
            if (response is NetworkResource.Fail) {
                _authorsState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = response.error
                    )
                }
            }
        }
    }

    fun saveAuthor(authorName: String, displayToast: () -> Unit) {
        viewModelScope.launch {
            val author = Author(name = authorName)

            poetryRepo.insertAuthorLocal(author).onSuccess {
                _authorsState.update {
                    val updatedAuthors = it.authorsList.map { author ->
                        if (author.name == authorName) author.copy(isSaved = true) else author
                    }
                    it.copy(
                        authorsList = updatedAuthors
                    )
                }
                displayToast()
            }.onFailure {
                _authorsState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun deleteAuthor(authorName: String) {
        viewModelScope.launch {
            poetryRepo.deleteAuthorLocal(authorName).onSuccess {
                _authorsState.update {
                    val updatedAuthors = it.authorsList.map { author ->
                        if (author.name == authorName) author.copy(isSaved = false) else author
                    }
                    it.copy(
                        authorsList = updatedAuthors
                    )
                }
            }.onFailure {
                _authorsState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun selectAuthor(authorName: String) {
        viewModelScope.launch {
            val response = poetryRepo.getTitlesByAuthor(authorName)
            if (response is NetworkResource.Success) {
                val poems = response.data
                _authorsState.update {
                    it.copy(
                        selectedAuthor = authorName,
                        poemsList = poems
                    )
                }
            }
            if (response is NetworkResource.Fail){
                _authorsState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = response.error
                    )
                }
            }
        }
    }

    fun selectPoem(authorName: String, tile: String) {
        _authorsState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = poetryRepo.getPoem(authorName, tile)
            if (response is NetworkResource.Success) {
                val poem = response.data.first()
                _authorsState.update {
                    it.copy(poemSelected = poem, isLoading = false, isSuccess = true)
                }
            }

            if (response is NetworkResource.Fail) {
                _authorsState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = response.error
                    )
                }
            }

        }
    }


    fun backToAuthors() {
        _authorsState.update {
            it.copy(
                selectedAuthor = null,
                poemsList = listOf()
            )
        }
    }

    fun backToPoems() {
        _authorsState.update {
            it.copy(poemSelected = null)
        }
    }


}