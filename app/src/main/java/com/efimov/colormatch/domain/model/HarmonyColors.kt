package com.efimov.colormatch.domain.model

import com.efimov.colormatch.domain.utils.PaletteColor

data class HarmonyColors(
    val primary: PaletteColor,
    val complementary: List<PaletteColor>,
    val analogous: List<PaletteColor>,
    val triadic: List<PaletteColor>
)
