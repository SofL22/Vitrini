package com.vitrini.app.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vitrini.app.ui.navigation.AppNavigation
import com.vitrini.app.ui.theme.VitriniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VitriniTheme {
                AppNavigation()
            }
        }
    }
}