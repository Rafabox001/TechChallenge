package com.example.baubaptechchallenge.presentation.login.validation

sealed class LoginEvent {
    data class UserNameChanged(val userName: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Submit : LoginEvent()

}