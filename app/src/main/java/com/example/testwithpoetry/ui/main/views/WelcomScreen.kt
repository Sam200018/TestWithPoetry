package com.example.testwithpoetry.ui.main.views

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testwithpoetry.R
import com.example.testwithpoetry.ui.components.DatePickerModal
import com.example.testwithpoetry.ui.components.LoadingDialogComponent
import com.example.testwithpoetry.ui.components.TextFieldComponent
import com.example.testwithpoetry.ui.main.interactions.FormState
import com.example.testwithpoetry.ui.main.interactions.UserInfoState
import com.example.testwithpoetry.ui.main.viewmodels.MainViewModel
import com.example.testwithpoetry.ui.main.viewmodels.WelcomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isFormValid(formState: FormState, welcomeViewModel: WelcomeViewModel): Boolean {
    return formState.isNameValid &&
            formState.isEmailValid &&
            formState.isBirthdayValid &&
            welcomeViewModel.nameVal.isNotBlank() &&
            welcomeViewModel.emailVal.isNotBlank() &&
            welcomeViewModel.birthdayVal != 0L
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    userInfoState: UserInfoState
) {

    val welcomeViewModel: WelcomeViewModel = hiltViewModel()
    val formState by welcomeViewModel.formState.collectAsStateWithLifecycle()
    var showModal by remember { mutableStateOf(false) }
    val source = remember { MutableInteractionSource() }

    Scaffold(
        floatingActionButton = {
            if (isFormValid(formState, welcomeViewModel)) {
                FloatingActionButton(onClick = {
                    mainViewModel.setUserInfo(
                        welcomeViewModel.emailVal,
                        welcomeViewModel.nameVal,
                        welcomeViewModel.birthdayVal
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.Done, contentDescription = "",
                        tint = colorResource(
                            id = R.color.black
                        ),
                        modifier = modifier.size(40.dp)
                    )
                }
            }
        }
    ) {
        Box(modifier.padding(it)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                TextFieldComponent(
                    modifier = modifier.fillMaxWidth(),
                    value = welcomeViewModel.nameVal,
                    label = stringResource(R.string.name_label),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isError = formState.isNameValid.not(),
                    errorMsg = stringResource(R.string.error_field_label),
                    onChange = {
                        welcomeViewModel.nameChanged(it)
                    }
                )
                Spacer(modifier.height(20.dp))
                TextFieldComponent(
                    modifier = modifier.fillMaxWidth(),
                    value = welcomeViewModel.emailVal,
                    label = stringResource(R.string.email_label),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = formState.isEmailValid.not(),
                    errorMsg = stringResource(R.string.error_field_label),
                    onChange = {
                        welcomeViewModel.emailChanged(it)
                    }
                )
                Spacer(modifier.height(20.dp))
                TextFieldComponent(
                    modifier = modifier.fillMaxWidth(),
                    interactionSource = source,
                    readOnly = false,
                    value = if (welcomeViewModel.birthdayVal == 0L) {
                        ""
                    } else {
                        convertMillisToDate(welcomeViewModel.birthdayVal)
                    },
                    label = stringResource(R.string.birthday_label),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = formState.isBirthdayValid.not(),
                    errorMsg = stringResource(R.string.error_field_label),

                    )
            }
            if (source.collectIsPressedAsState().value) {
                showModal = true
            }

            if (showModal) {
                DatePickerModal(
                    onDateSelected = {
                        welcomeViewModel.birthdayChange(it)
                    },
                    onDismiss = {
                        showModal = false
                    }
                )
            }

            if (userInfoState.isLoading) {
                LoadingDialogComponent()
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val date = Date(millis)
    val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.UK).format(date)
    return formattedDate
}