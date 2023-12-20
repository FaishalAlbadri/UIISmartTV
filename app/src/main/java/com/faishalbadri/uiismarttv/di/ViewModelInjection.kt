package com.faishalbadri.uiismarttv.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.faishalbadri.uiismarttv.utils.LocationPreferences


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("com.faishalbadri.uiismarttv.preferences")

object ViewModelInjection {
    fun provideRepository(context: Context): LocationPreferences {
        return LocationPreferences.getInstance(context.dataStore)
    }
}