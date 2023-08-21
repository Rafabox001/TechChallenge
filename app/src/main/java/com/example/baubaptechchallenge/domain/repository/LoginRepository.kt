package com.example.baubaptechchallenge.domain.repository

import com.example.baubaptechchallenge.domain.value.LoginCredentials
import com.example.baubaptechchallenge.domain.value.LoginStatus
import kotlinx.coroutines.flow.Flow


interface LoginRepository {
    fun attemptLogin(loginCredentials: LoginCredentials): Flow<LoginStatus>
}