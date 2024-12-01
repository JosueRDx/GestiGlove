package com.josuerdx.gestiglove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.josuerdx.gestiglove.navigation.AppNavigation
import com.josuerdx.gestiglove.repository.ThemeRepository
import com.josuerdx.gestiglove.ui.theme.GestiGloveTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themeRepository = ThemeRepository.getInstance(applicationContext)
        val initialTheme = runBlocking {
            themeRepository.selectedTheme.first()
        }
        setContent {
            GestiGloveTheme(
                themeRepository = themeRepository,
                dynamicColor = true,
                initialTheme = initialTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
}