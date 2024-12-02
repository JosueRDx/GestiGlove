package com.josuerdx.gestiglove.screens.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.josuerdx.gestiglove.R

/**
 * Pantalla de Ayuda para explicar las funcionalidades.
 */
@Composable
fun HelpScreen() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(top = 120.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "Logo App",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .padding(bottom = 16.dp)
            )

            // Uso de Mis Gestos
            HelpCard(
                title = "Mis Gestos:",
                description = "Accede a 'Mis Gestos' para gestionar y personalizar tus gestos. Podrás agregar nuevos, editarlos o eliminarlos según tus necesidades."
            )

            // Uso de Perfil
            HelpCard(
                title = "Perfil:",
                description = "La sección de Perfil permite visualizar tus datos personales, como correo y nombre, pero no permite editarlos. Para cambios, contacta con soporte."
            )

            // General
            HelpCard(
                title = "¿Cómo funciona GestiGlove?",
                description = "GestiGlove convierte gestos en mensajes personalizados para mejorar la comunicación. Configura tus preferencias desde el menú principal."
            )

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

/**
 * Componente para mostrar información.
 */
@Composable
fun HelpCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpScreenPreview() {
    HelpScreen()
}