package com.example.testwithpoetry.ui.main.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.testwithpoetry.ui.main.interactions.FormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
) : ViewModel() {
    private val _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()

    var nameVal by mutableStateOf("")
        private set

    var emailVal by mutableStateOf("")
        private set

    var birthdayVal by mutableLongStateOf(0)
        private set

    fun nameChanged(name: String) {
        nameVal = name
        _formState.update {
            it.copy(isNameValid = nameVal.isNotBlank())
        }
    }

    fun emailChanged(email: String) {
        emailVal = email
        _formState.update {
            it.copy(
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()
            )
        }
    }

    fun birthdayChange(birthday: Long?) {
        if(birthday!=null){
            birthdayVal = birthday
            _formState.update {
                it.copy(isBirthdayValid = true)
            }
        }else{
            _formState.update {
                it.copy(isBirthdayValid = false)
            }
        }
    }


}