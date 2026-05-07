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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.theme.KinoDarkGray

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = viewModel(),
    onNavigateToAccountInfo: () -> Unit,
    onLogout: () -> Unit,
) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))

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
                .background(KinoDarkGray)
                .padding(20.dp)
        ) {

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
                if (state.isLoading) {
                    CircularProgressIndicator(color = KinoYellow)
                } else if (state.errorMsg != null) {
                    Text(text = state.errorMsg!!, color = Color.Red)
                } else {
                Text(text = state.userName,
                    color = KinoWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(color = KinoYellow)
                } else if (state.errorMsg != null) {
                    Text(text = state.errorMsg!!, color = Color.Red)
                } else {
                    Text(text = state.email,
                        color = KinoWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

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
                .background(KinoDarkGray)
        ) {
            ProfileOptionItem(
                icon = Icons.Outlined.Share,
                text = "Share your statistics",
                onClick = { /* TODO */ }
            )

            HorizontalDivider(color = KinoBlack, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            ProfileOptionItem(
                icon = Icons.Outlined.Info,
                text = "Change your password",
                onClick = onNavigateToAccountInfo
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KinoDarkGray)
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