package com.example.baubaptechchallenge.data.repository

import com.example.baubaptechchallenge.domain.repository.LoginRepository
import com.example.baubaptechchallenge.domain.value.LoginCredentials
import com.example.baubaptechchallenge.domain.value.LoginStatus
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataStoreLoginRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): LoginRepository {

    override fun attemptLogin(loginCredentials: LoginCredentials) = flow {
        if (localDataSource.attemptLogin(loginCredentials = loginCredentials))
            emit(LoginStatus.LoginSuccess)
        else emit(LoginStatus.LoginFailure)
    }
}