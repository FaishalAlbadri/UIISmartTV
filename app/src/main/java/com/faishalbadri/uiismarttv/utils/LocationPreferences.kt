package com.faishalbadri.uiismarttv.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import com.faishalbadri.uiismarttv.data.local.LocationDataPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val ID_KEY = stringPreferencesKey("id")
    private val NAMA_KEY = stringPreferencesKey("nama")

    fun getLocation(): Flow<LocationDataPreferences> {
        return dataStore.data.map { preferences ->
            LocationDataPreferences(
                preferences[ID_KEY] ?: "",
                preferences[NAMA_KEY] ?: ""
            )
        }
    }

    suspend fun saveLocation(location: LocationDataPreferences) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = location.id
            preferences[NAMA_KEY] = location.nama
        }
    }

    suspend fun deleteLocation() {
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