package com.example.testwithpoetry.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.data.user.UserRepository
import com.example.testwithpoetry.localModels.User
import com.example.testwithpoetry.ui.main.interactions.UserInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow(UserInfoState())
    val userInfo = _userInfo.asStateFlow()

    init {
        _userInfo.update {
            it.copy(isLoading = true)
        }
        getInfoUser()
    }

    private fun getInfoUser() {


        viewModelScope.launch {
            val resp = userRepository.getUserInfo()
            resp.onSuccess { value ->
                _userInfo.update {
                    it.copy(user = value, isLoading = false, isSuccess = true)
                }
            }.onFailure { err ->
                _userInfo.update {
                    it.copy(
                        message = err.message ?: "Error desconocido",
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun setUserInfo(email: String, name: String, birthday: Long) {
        _userInfo.update {
            it.copy(isLoading = true)
        }
        val user = User(name, email, birthday)
        viewModelScope.launch {
            userRepository.insertUserInfo(user).onSuccess {
                getInfoUser()
            }.onFailure { err ->
                _userInfo.update {
                    it.copy(
                        message = err.message ?: "Error desconocido",
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

}