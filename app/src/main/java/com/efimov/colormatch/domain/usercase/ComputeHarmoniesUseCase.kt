package com.efimov.colormatch.domain.usercase

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.efimov.colormatch.domain.model.HarmonyColors
import javax.inject.Inject
import android.graphics.Color as AndroidColor

class ComputeHarmoniesUseCase @Inject constructor(
    private val getClosestColor: GetClosestColorUseCase
) {
    operator fun invoke(baseColor: Color): HarmonyColors {
        val hsv = FloatArray(3)
        AndroidColor.colorToHSV(baseColor.toArgb(), hsv)
        val hue = hsv[0]
        val sat = hsv[1]
        val value = hsv[2]

        fun colorFromHue(h: Float): Color {
            val rgb = AndroidColor.HSVToColor(floatArrayOf((h + 360) % 360, sat, value))
            return Color(rgb)
        }

        val primary = getClosestColor(baseColor)

        val complementary = getClosestColor(colorFromHue(hue + 180))
        val analogous1 = getClosestColor(colorFromHue(hue - 30))
        val analogous2 = getClosestColor(colorFromHue(hue + 30))
        val triadic1 = getClosestColor(colorFromHue(hue + 120))
        val triadic2 = getClosestColor(colorFromHue(hue + 240))

        return HarmonyColors(
            primary = primary,
            complementary = listOf(complementary),
            analogous = listOf(analogous1, analogous2),
            triadic = listOf(triadic1, triadic2)
        )
    }
}