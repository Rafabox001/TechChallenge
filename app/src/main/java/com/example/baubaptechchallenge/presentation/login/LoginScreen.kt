package com.example.baubaptechchallenge.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.baubaptechchallenge.R.string
import com.example.baubaptechchallenge.presentation.login.components.CustomDialog
import com.example.baubaptechchallenge.presentation.login.components.LoginErrorLabel
import com.example.baubaptechchallenge.presentation.login.components.LoginTextField
import com.example.baubaptechchallenge.presentation.login.components.PrimaryButton
import com.example.baubaptechchallenge.presentation.login.validation.LoginEvent
import com.example.baubaptechchallenge.presentation.login.validation.LoginFormState
import com.example.baubaptechchallenge.presentation.login.validation.LoginValidationEvent
import com.example.baubaptechchallenge.presentation.theme.dimens
import com.example.baubaptechchallenge.presentation.util.UiText
import timber.log.Timber

const val CONTAINER_TEST_TAG = "login_screen_container"
const val USERNAME_FIELD_TEST_TAG = "username_input"
const val PASSWORD_FIELD_TEST_TAG = "password_input"
const val USERNAME_ERROR_TEST_TAG = "username_error_label"
const val PASSWORD_ERROR_TEST_TAG = "password_error_label"
const val LOGIN_BUTTON_TEST_TAG = "login_screen_button"
const val DIALOG_TEST_TAG = "login_screen_dialog"

@Composable
fun LoginScreen (
    loginViewModel: LoginViewModel
) {
    val isSuccessDialogShown = remember {
        mutableStateOf(false)
    }
    val isFailureDialogShown = remember {
        mutableStateOf(false)
    }

    val state by loginViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        loginViewModel.validationEvents.collect { event ->
            when (event) {
                is LoginValidationEvent.Success -> {
                    Timber.d("Show successful login Dialog")
                    isSuccessDialogShown.value = true
                }
                is LoginValidationEvent.Failure -> {
                    Timber.d("Show failure login Dialog")
                    isFailureDialogShown.value = true
                }
            }
        }
    }

    LoginScreenComponent(
        state = state,
        isSuccessDialogShown = isSuccessDialogShown,
        isFailureDialogShown = isFailureDialogShown
    ) { event ->
        loginViewModel.onEvent(event)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenComponent (
    state: LoginFormState,
    isSuccessDialogShown: MutableState<Boolean>,
    isFailureDialogShown: MutableState<Boolean>,
    onEvent: (LoginEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.normal
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag(CONTAINER_TEST_TAG),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val (passwordFocus, togglePasswordFocus) = remember { FocusRequester.createRefs() }

            var showPassword by remember {
                mutableStateOf(false)
            }
            val localFocusManager = LocalFocusManager.current

            val columnContentWidth = .7f

            if (state.showLoading) {
                Dialog(onDismissRequest = { }) {
                    CircularProgressIndicator()
                }
            }

            val showUserError = state.userNameError?.asString() != null

            state.responseError?.let { errorMessage ->
                ResponseValidationError(error = errorMessage)
            }

            LoginTextField(
                modifier = Modifier
                    .fillMaxWidth(columnContentWidth)
                    .testTag(USERNAME_FIELD_TEST_TAG),
                value = state.userName,
                label = stringResource(id = string.login_screen_username_hint),
                showError = showUserError,
                trailingIcon = {
                },
                onValueChange = { onEvent(LoginEvent.UserNameChanged(it)) }
            )

            state.userNameError?.let { error ->
                LoginErrorLabel(
                    modifier = Modifier
                        .fillMaxWidth(columnContentWidth)
                        .testTag(USERNAME_ERROR_TEST_TAG),
                    error.asString()
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.normal))

            val showPasswordError = state.passwordError?.asString() != null

            LoginTextField(
                modifier = Modifier
                    .fillMaxWidth(columnContentWidth)
                    .focusRequester(passwordFocus)
                    .focusProperties { right = togglePasswordFocus }
                    .testTag(PASSWORD_FIELD_TEST_TAG),
                value = state.password,
                label = stringResource(id = string.login_screen_password_hint),
                showError = showPasswordError,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword },
                        modifier = Modifier
                            .focusRequester(togglePasswordFocus)
                            .focusProperties { left = passwordFocus }
                    ) {
                        Icon(
                            imageVector =
                            if (showPassword) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff, "",
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                    }
                },
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) }
            )

            state.passwordError?.let { error ->
                LoginErrorLabel(
                    modifier = Modifier
                        .fillMaxWidth(columnContentWidth)
                        .testTag(PASSWORD_ERROR_TEST_TAG),
                    error.asString()
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

            PrimaryButton(
                text = stringResource(string.login),
                modifier = Modifier
                    .fillMaxWidth(columnContentWidth)
                    .testTag(LOGIN_BUTTON_TEST_TAG),
                onClick = {
                    localFocusManager.clearFocus()
                    onEvent(LoginEvent.Submit)
                },
            )

            if (isSuccessDialogShown.value) {
                CustomDialog(openDialogCustom = isSuccessDialogShown)
            }
            if (isFailureDialogShown.value) {
                CustomDialog(
                    openDialogCustom = isFailureDialogShown,
                    isErrorDialog = true
                )
            }
        }
    }
}

@Composable
private fun ResponseValidationError (error: UiText) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.size(MaterialTheme.dimens.xxSmall))
        LoginErrorLabel(modifier = Modifier.wrapContentWidth(), text = error.asString())
    }
}