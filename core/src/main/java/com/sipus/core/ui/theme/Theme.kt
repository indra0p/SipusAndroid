package com.sipus.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = PrimaryDark,
    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = SecondaryDark,
    tertiary = Tertiary,
    tertiaryContainer = TertiaryContainer,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    onSurfaceVariant = TextSecondaryLight,
    background = SurfaceLight,
    onBackground = TextPrimaryLight,
    error = StatusError,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFF1F5F9),
    surfaceContainer = Color(0xFFE2E8F0),
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = PrimaryDark,
    primaryContainer = Color(0xFF312E81),
    onPrimaryContainer = Color(0xFFC7D2FE),
    secondary = Color(0xFF2DD4BF),
    onSecondary = SecondaryDark,
    secondaryContainer = Color(0xFF134E4A),
    onSecondaryContainer = Color(0xFF99F6E4),
    tertiary = Color(0xFFFBBF24),
    tertiaryContainer = Color(0xFF78350F),
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    onSurfaceVariant = TextSecondaryDark,
    background = SurfaceDark,
    onBackground = TextPrimaryDark,
    error = Color(0xFFFCA5A5),
    surfaceContainerLowest = Color(0xFF0F172A),
    surfaceContainerLow = CardDark,
    surfaceContainer = Color(0xFF334155),
)

private val AppTypography = Typography(
    headlineLarge = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 36.sp),
    headlineMedium = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp),
    headlineSmall = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp),
    titleLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, lineHeight = 22.sp),
    titleSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium),
)

@Composable
fun SipusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        typography = AppTypography,
        content = content
    )
}
