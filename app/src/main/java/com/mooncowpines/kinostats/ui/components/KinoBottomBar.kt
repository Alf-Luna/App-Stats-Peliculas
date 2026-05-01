package com.mooncowpines.kinostats.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults

import com.mooncowpines.kinostats.navigation.Route
import com.mooncowpines.kinostats.ui.theme.KinoTransparentBlack
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoBottomBar(currentRoute: String, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = KinoTransparentBlack,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Route.Home.path,
            onClick = { onNavigate(Route.Home.path) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KinoYellow,
                unselectedIconColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == Route.Stats.path,
            onClick = { onNavigate(Route.Stats.path) },
            icon = { Icon(Icons.Filled.PieChart, contentDescription = "Stats") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KinoYellow,
                unselectedIconColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == Route.Profile.path,
            onClick = { onNavigate(Route.Profile.path) },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KinoYellow,
                unselectedIconColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == Route.Test.path,
            onClick = { onNavigate(Route.Test.path) },
            icon = { Icon(Icons.Filled.Info, contentDescription = "Test") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KinoYellow,
                unselectedIconColor = Color.DarkGray,
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000, widthDp = 80, heightDp = 10, name = "BottomBar")
@Composable
fun KinoBottomBarPreview() {
    KinoBottomBar(currentRoute = "currentRoute", onNavigate = {})
}