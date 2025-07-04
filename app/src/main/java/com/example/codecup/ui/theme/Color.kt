package com.example.codecup.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

// Light mode colors
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF324A59),
    background = Color.White,
    onBackground = Color(0xFF0D1A26),
    surface = Color.White,
    onSurface = Color.Black
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF66A6FF),         // Xanh thiên thanh dịu nhẹ
    background = Color(0xFF1C262F),      // Xám xanh đậm - nền chính
    onBackground = Color(0xFFF1F3F5),    // Text sáng nhưng không trắng gắt
    surface = Color(0xFF2A343E),         // Card hơi nổi so với nền
    onSurface = Color(0xFFE0E3E7)        // Text trên thẻ
)

