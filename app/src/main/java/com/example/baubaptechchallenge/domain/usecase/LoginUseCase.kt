package com.example.baubaptechchallenge.domain.usecase

import com.example.baubaptechchallenge.domain.repository.LoginRepository
import com.example.baubaptechchallenge.domain.value.LoginCredentials
import com.example.baubaptechchallenge.domain.value.LoginStatus
import com.example.baubaptechchallenge.presentation.util.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    fun execute(loginCredentials: LoginCredentials): Flow<LoginStatus> =
        when {
            loginCredentials.userName.isBlank() && loginCredentials.password.isBlank() ->
                LoginStatus.EmptyUserNameAndPassword.asFlow()

            loginCredentials.userName.isBlank() -> LoginStatus.EmptyUserName.asFlow()

            loginCredentials.password.isBlank() -> LoginStatus.EmptyPassword.asFlow()

            loginCredentials.password.length < PASSWORD_MIN_LENGTH ->
                LoginStatus.InvalidFormat(PASSWORD_MIN_LENGTH).asFlow()

            else -> loginRepository.attemptLogin(loginCredentials = loginCredentials)
                .catch {
                    emit(LoginStatus.CouldNotFinishLoginAttempt)
                }
        }

    private companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }

}