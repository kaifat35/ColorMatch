package com.efimov.colormatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.efimov.colormatch.presentation.navigation.AppNavigation
import com.efimov.colormatch.presentation.ui.theme.ColorMatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorMatchTheme {
                AppNavigation()
            }
        }
    }
}