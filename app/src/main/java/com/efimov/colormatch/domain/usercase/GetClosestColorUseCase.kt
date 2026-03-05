package com.efimov.colormatch.domain.usercase

import androidx.compose.ui.graphics.Color
import com.efimov.colormatch.domain.utils.BasicPalette
import com.efimov.colormatch.domain.utils.PaletteColor
import javax.inject.Inject

class GetClosestColorUseCase @Inject constructor() {
    operator fun invoke(target: Color): PaletteColor {
        return BasicPalette.findClosest(target)
    }
}