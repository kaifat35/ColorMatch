package com.efimov.colormatch.domain.usercase

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color

interface ImageProcessor {
    fun cropCenterSquare(bitmap: Bitmap): Bitmap
    fun computeAverageColor(bitmap: Bitmap): Color
    fun scaleBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap
}