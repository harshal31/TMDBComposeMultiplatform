package com.compose.starter.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.poppins_black
import composestarter.composeapp.generated.resources.poppins_bold
import composestarter.composeapp.generated.resources.poppins_extra_bold
import composestarter.composeapp.generated.resources.poppins_extra_light
import composestarter.composeapp.generated.resources.poppins_italic
import composestarter.composeapp.generated.resources.poppins_light
import composestarter.composeapp.generated.resources.poppins_medium
import composestarter.composeapp.generated.resources.poppins_normal
import composestarter.composeapp.generated.resources.poppins_semi_bold
import composestarter.composeapp.generated.resources.poppins_thin
import org.jetbrains.compose.resources.Font

@Composable
private fun RobotoMonoFontFamily() = FontFamily(
    Font(Res.font.poppins_light, FontWeight.Light),
    Font(Res.font.poppins_medium, FontWeight.Medium),
    Font(Res.font.poppins_bold, FontWeight.Bold),
    Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(Res.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(Res.font.poppins_thin, FontWeight.Thin),
    Font(Res.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.poppins_normal, FontWeight.Normal),
    Font(Res.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(Res.font.poppins_black, FontWeight.Black)
)

@Composable
internal fun TMDBTypography() = Typography().run {
    val fontFamily = RobotoMonoFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}