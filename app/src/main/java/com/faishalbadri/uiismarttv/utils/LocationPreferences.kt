package com.faishalbadri.uiismarttv.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import com.faishalbadri.uiismarttv.data.local.LocationDataPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val PROVINSI_KEY = stringPreferencesKey("provinsi")
    private val KOTA_KEY = stringPreferencesKey("kota")

    fun getLocation(): Flow<LocationDataPreferences> {
        return dataStore.data.map { preferences ->
            LocationDataPreferences(
                preferences[PROVINSI_KEY] ?: "",
                preferences[KOTA_KEY] ?: ""
            )
        }
    }

    suspend fun saveLocation(location: LocationDataPreferences) {
        dataStore.edit { preferences ->
            preferences[PROVINSI_KEY] = location.provinsi
            preferences[KOTA_KEY] = location.kota
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LocationPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): LocationPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = LocationPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}