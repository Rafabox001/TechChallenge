package com.example.baubaptechchallenge.domain.value

sealed class LoginStatus {
    object LoginSuccess : LoginStatus()
    object LoginFailure: LoginStatus()
    object CouldNotFinishLoginAttempt: LoginStatus()
    object EmptyUserName : LoginStatus()
    object EmptyPassword : LoginStatus()
    object EmptyUserNameAndPassword : LoginStatus()
    data class InvalidFormat(val requiredLength: Int) : LoginStatus()
}