package com.example.jetpack_compose_assignment_2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Color.Black,
    secondary = GreenGrey80,
    onSecondary = Color.Black,
    tertiary = Lime80,
    onTertiary = Color.Black,
    error = ErrorRed,
    surfaceVariant = SurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    secondary = GreenGrey40,
    onSecondary = Color.White,
    tertiary = Lime40,
    onTertiary = Color.Black,
    error = ErrorRed,
    surfaceVariant = SurfaceVariant

    /* Other default colors to override
    background = Color(0xFFF8FFF8), // very light greenish white
    surface = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Jetpackcomposeassignment2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}