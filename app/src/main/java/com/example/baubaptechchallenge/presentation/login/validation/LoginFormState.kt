package com.example.baubaptechchallenge.presentation.login.validation

import com.example.baubaptechchallenge.presentation.util.UiText

data class LoginFormState(
    val userName: String = "",
    val password: String = "",
    val userNameError: UiText? = null,
    val passwordError: UiText? = null,
    val responseError: UiText? = null,
    val showLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)