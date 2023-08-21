package com.example.baubaptechchallenge.di.module

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_PREFERENCES = "app_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCES)

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {
    @Singleton
    @Provides
    fun provideAppDataStore(appContext: Application): DataStore<Preferences> {
        return appContext.dataStore
    }
}