package com.example.baubaptechchallenge.presentation.login.components

import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.KEYCODE_TAB
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    modifier: Modifier,
    value: String,
    label: String,
    showError: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent {
                if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER ||
                    it.nativeKeyEvent.keyCode == KEYCODE_TAB
                ) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else false
            },
        value = value,
        isError = showError,
        trailingIcon = { trailingIcon() },
        label = { Text(label, color = MaterialTheme.colorScheme.inverseSurface) },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        onValueChange = { changedValue -> onValueChange(changedValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
    )
}