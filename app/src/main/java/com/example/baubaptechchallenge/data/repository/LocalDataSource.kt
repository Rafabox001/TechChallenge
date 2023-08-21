package com.example.baubaptechchallenge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.baubaptechchallenge.domain.value.LoginCredentials
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    init {
        loadInitialData()
    }

    private fun loadInitialData() = runBlocking {
        val testUser = LoginCredentials(
            userName = USERNAME_VALUE,
            password = PASSWORD_VALUE
        )
        saveCredentials(testUser)
    }

    private suspend fun getPreferenceString(key: Preferences.Key<String>): String? {
        val preferences = dataStore.data.first()
        return preferences[key]
    }

    suspend fun attemptLogin(loginCredentials: LoginCredentials): Boolean {
        return loginCredentials.userName == getPreferenceString(USERNAME_KEY)
                && loginCredentials.password == getPreferenceString(PASSWORD_KEY)
    }

    private suspend fun saveCredentials(loginCredentials: LoginCredentials) {
        Timber.d("Saving credentials")

        dataStore.edit {
            it[USERNAME_KEY] = loginCredentials.userName
            it[PASSWORD_KEY] = loginCredentials.password
        }

        Timber.d("Credentials saved")
    }

    private companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val PASSWORD_KEY = stringPreferencesKey("password")
        const val USERNAME_VALUE = "TestUser"
        const val PASSWORD_VALUE = "Pass123"
    }
}