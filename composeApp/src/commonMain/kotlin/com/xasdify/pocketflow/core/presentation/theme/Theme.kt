package com.xasdify.pocketflow.core.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val Navy_Blue = Color(0XFF0D47A1)
val Emerald_Green = Color(0XFF006E33)
private val LightColorScheme = lightColorScheme(
    primary = Navy_Blue,
    secondary = Emerald_Green,
)

private val DarkColorScheme = darkColorScheme(
    primary = Navy_Blue,
    secondary = Emerald_Green,
)

@Composable
fun AppTheme(darkTheme: Boolean = false, content: @Composable() () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(colorScheme = colors) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            content()
        }
    }
}