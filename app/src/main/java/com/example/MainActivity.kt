package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.navigation.RedlineAppShell
import com.example.ui.theme.RedlineTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.RedlineViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        val app = application as RedlineApplication
        setContent {
            RedlineTheme {
                val mainViewModel: MainViewModel = viewModel(
                    factory = RedlineViewModelFactory(app)
                )
                RedlineAppShell(viewModel = mainViewModel)
            }
        }
    }
}
