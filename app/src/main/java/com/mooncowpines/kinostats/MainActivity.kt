package com.mooncowpines.kinostats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mooncowpines.kinostats.navigation.NavGraph
import com.mooncowpines.kinostats.ui.screens.login.LoginScreen
import com.mooncowpines.kinostats.ui.screens.login.LoginScreenViewModel
import com.mooncowpines.kinostats.ui.screens.register.RegisterScreen
import com.mooncowpines.kinostats.ui.theme.KinoStatsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KinoStatsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

