package com.josuerdx.gestiglove.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josuerdx.gestiglove.R

/**
 * Componente que muestra el ícono en un contenedor circular.
 */
@Composable
fun ProfileIcon() {
    Box(
        modifier = Modifier
            .size(145.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(145.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 8.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Ícono de perfil"
            )
        }
    }
}

@Preview(showBackground = true, name = "Profile Icon Preview")
@Composable
fun ProfileIconPreview() {
    ProfileIcon()
}