package com.example.baubaptechchallenge.presentation.login.validation

sealed class LoginValidationEvent {
    object Success: LoginValidationEvent()
    object Failure: LoginValidationEvent()
}