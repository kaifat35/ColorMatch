package com.efimov.colormatch.domain.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.efimov.colormatch.domain.utils.PaletteColor

data class ProcessedImageResult(
    val thumbnail: Bitmap,
    val averageColor: Color,
    val closestPaletteColor: PaletteColor,
    val rgbHex: String
)
