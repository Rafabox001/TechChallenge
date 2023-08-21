package com.example.baubaptechchallenge.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.baubaptechchallenge.data.repository.DataStoreLoginRepository
import com.example.baubaptechchallenge.data.repository.LocalDataSource
import com.example.baubaptechchallenge.domain.repository.LoginRepository
import com.example.baubaptechchallenge.domain.usecase.LoginUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providesLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesLoginLocalDataSource(dataStore: DataStore<Preferences>): LocalDataSource {
        return LocalDataSource(dataStore)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindRepository {
        @Binds
        @Singleton
        fun bindRepository(loginRepository: DataStoreLoginRepository): LoginRepository
    }
}