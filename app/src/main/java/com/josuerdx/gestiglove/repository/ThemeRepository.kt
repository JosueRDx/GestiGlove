package com.josuerdx.gestiglove.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepository private constructor(private val context: Context) {
    companion object {
        const val THEME_LIGHT = "Tema Claro"
        const val THEME_DARK = "Tema Oscuro"
        private val THEME_KEY = stringPreferencesKey("selected_theme")

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ThemeRepository? = null

        fun getInstance(context: Context): ThemeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemeRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // Crea DataStore con extensión de contexto
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

    // Para obtener el tema actual
    val selectedTheme: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: THEME_LIGHT
        }

    // Guardar el tema
    suspend fun saveSelectedTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
}