package com.example.baubaptechchallenge.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baubaptechchallenge.R.string
import com.example.baubaptechchallenge.domain.usecase.LoginUseCase
import com.example.baubaptechchallenge.domain.value.LoginCredentials
import com.example.baubaptechchallenge.domain.value.LoginStatus
import com.example.baubaptechchallenge.presentation.login.validation.LoginEvent
import com.example.baubaptechchallenge.presentation.login.validation.LoginFormState
import com.example.baubaptechchallenge.presentation.login.validation.LoginValidationEvent
import com.example.baubaptechchallenge.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(LoginFormState())
    val state = mutableState.asStateFlow()

    private val validationEventChannel = Channel<LoginValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UserNameChanged -> {
                mutableState.update {
                    it.copy(userName = event.userName.trim())
                }
            }

            is LoginEvent.PasswordChanged -> {
                mutableState.update {
                    it.copy(password = event.password.trim())
                }
            }

            is LoginEvent.Submit -> {
                attemptLogin()
            }
        }
    }

    private fun attemptLogin() {
        showLoading()

        val loginCredentials = LoginCredentials(
            userName = state.value.userName,
            password = state.value.password
        )

        viewModelScope.launch {
            try {
                loginUseCase.execute(loginCredentials = loginCredentials)
                    .collect { response ->
                        when(response) {
                            LoginStatus.LoginSuccess -> {
                                Timber.d("Login response is Success")
                                hideErrors()
                                validationEventChannel.send(LoginValidationEvent.Success)
                            }
                            LoginStatus.LoginFailure -> {
                                Timber.d("Login response is Failure")
                                hideErrors()
                                validationEventChannel.send(LoginValidationEvent.Failure)
                            }
                            LoginStatus.CouldNotFinishLoginAttempt -> {
                                Timber.d("Login could not finish")
                                val message = UiText.StringResource(
                                    string.login_screen_generic_error
                                )
                                showResponseError(error = message)
                            }
                            LoginStatus.EmptyPassword -> {
                                Timber.d("Login response is EmptyPassword")
                                showPasswordError()
                            }
                            LoginStatus.EmptyUserName -> {
                                Timber.d("Login response is EmptyUserName")
                                showUserNameError()
                            }
                            LoginStatus.EmptyUserNameAndPassword -> {
                                Timber.d("Login response is both fields empty")
                                showUserNameAndPasswordError()
                            }
                            is LoginStatus.InvalidFormat -> {
                                Timber.d("Login response is invalidFormat")
                                val message = UiText.StringResource(
                                    string.login_screen_validation_password_wrong_format,
                                    response.requiredLength
                                )
                                showPasswordFormatError(message = message)
                            }
                        }
                    }
            } catch (e: Exception) {
                Timber.d("Login Response had an unexpected exception: ${e.message}")
                val exception = UiText.DynamicString(e.message ?: "")
                showResponseError(exception)
            }

            hideLoading()
        }
    }

    private fun showLoading() {
        mutableState.update {
            it.copy(
                showLoading = true
            )
        }
    }

    private fun hideLoading() {
        mutableState.update {
            it.copy(
                showLoading = false
            )
        }
    }

    private fun hideErrors() {
        mutableState.update {
            it.copy(
                userNameError = null,
                passwordError = null,
                responseError = null
            )
        }
    }

    private fun showPasswordError() {
        mutableState.update {
            it.copy(
                passwordError = UiText.StringResource(string.login_screen_validation_password_empty),
                userNameError = null,
                responseError = null
            )
        }
    }

    private fun showPasswordFormatError(message: UiText) {
        mutableState.update {
            it.copy(
                passwordError = message,
                userNameError = null,
                responseError = null
            )
        }
    }

    private fun showUserNameError() {
        mutableState.update {
            it.copy(
                userNameError = UiText.StringResource(string.login_screen_validation_username_empty),
                passwordError = null,
                responseError = null
            )
        }
    }

    fun showUserNameAndPasswordError() {
        mutableState.update {
            it.copy(
                userNameError = UiText.StringResource(string.login_screen_validation_username_empty),
                passwordError = UiText.StringResource(string.login_screen_validation_password_empty),
                responseError = null
            )
        }
    }

    private fun showResponseError(error: UiText) {
        mutableState.update {
            it.copy(
                responseError = error,
                passwordError = null,
                userNameError = null
            )
        }
    }
}