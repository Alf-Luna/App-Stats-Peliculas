package com.mooncowpines.kinostats.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.data.User
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

// Color gris oscuro para las tarjetas, resalta sobre el KinoBlack
val KinoDarkCard = Color(0xFF1E1E1E)

@Composable
fun ProfileScreen(
    user: User,
    onNavigateToAccountInfo: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // --- 1. TARJETA DE USUARIO (HEADER) ---
        Text(
            text = "PROFILE",
            color = Color.Gray,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KinoDarkCard)
                .padding(20.dp)
        ) {
            // Fondo circular sutil para el avatar
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(KinoYellow.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Avatar",
                    tint = KinoYellow,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = user.userName,
                    color = KinoWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = user.email,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- 2. TARJETA DE OPCIONES (GENERAL) ---
        Text(
            text = "GENERAL",
            color = Color.Gray,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KinoDarkCard)
        ) {
            ProfileOptionItem(
                icon = Icons.Outlined.Share,
                text = "Share your statistics",
                onClick = { /* TODO */ }
            )

            Divider(color = KinoBlack, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            ProfileOptionItem(
                icon = Icons.Outlined.Info,
                text = "Change your password",
                onClick = onNavigateToAccountInfo
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- 3. TARJETA DE CERRAR SESIÓN (AISLADA) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KinoDarkCard)
        ) {
            ProfileOptionItem(
                icon = Icons.AutoMirrored.Outlined.ExitToApp,
                text = "Log out of account",
                onClick = onLogout,
                isDestructive = true
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

// --- COMPONENTE AUXILIAR MEJORADO ---
@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val tintColor = if (isDestructive) Color(0xFFE57373) else KinoWhite
    val iconBgColor = if (isDestructive) Color(0xFFE57373).copy(alpha = 0.1f) else Color.DarkGray.copy(alpha = 0.3f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        // Ícono dentro de un pequeño círculo para darle más peso visual
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = tintColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = tintColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}