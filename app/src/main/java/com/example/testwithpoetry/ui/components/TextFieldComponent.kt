package com.example.testwithpoetry.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String = "",
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean = false,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    label: String = "",
    errorMsg: String = "",
    onChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        readOnly = readOnly,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            if (isError)
                Text(errorMsg)
        },
        onValueChange = {
            onChange(it)
        })
}