package com.xasdify.pocketflow.core.presentation.theme


import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pocketflow.composeapp.generated.resources.Res
import pocketflow.composeapp.generated.resources.acumin_variable_concept


@Composable
fun fontFamily() = FontFamily(
    Font(
        resource = Res.font.acumin_variable_concept,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.acumin_variable_concept,
        weight = FontWeight.Medium
    ),
    Font(
        resource = Res.font.acumin_variable_concept,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.acumin_variable_concept,
        weight = FontWeight.Bold
    ),
    Font(
        resource = Res.font.acumin_variable_concept,
        weight = FontWeight.Black
    )
)

@Composable
fun appTypography() = Typography().run {
    val fontFamily = fontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily, fontSize = 22.sp),
        displayMedium = displayMedium.copy(fontFamily = fontFamily, fontSize = 18.sp),
        displaySmall = displaySmall.copy(fontFamily = fontFamily, fontSize = 16.sp),

        headlineLarge = headlineLarge.copy(fontFamily = fontFamily, fontSize = 16.sp),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily, fontSize = 14.sp),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily, fontSize = 13.sp),

        titleLarge = titleLarge.copy(fontFamily = fontFamily, fontSize = 22.sp),
        titleMedium = titleMedium.copy(fontFamily = fontFamily, fontSize = 16.sp),
        titleSmall = titleSmall.copy(fontFamily = fontFamily, fontSize = 14.sp),

        bodyLarge = bodyLarge.copy(fontFamily = fontFamily, fontSize = 16.sp),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily, fontSize = 14.sp),
        bodySmall = bodySmall.copy(fontFamily = fontFamily, fontSize = 12.sp),

        labelLarge = labelLarge.copy(fontFamily = fontFamily, fontSize = 14.sp),
        labelMedium = labelMedium.copy(fontFamily = fontFamily, fontSize = 12.sp),
        labelSmall = labelSmall.copy(fontFamily = fontFamily, fontSize = 11.sp)
    )
}