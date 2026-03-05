package com.efimov.colormatch.data.image

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.efimov.colormatch.domain.usercase.ImageProcessor
import android.graphics.Color as AndroidColor
import androidx.core.graphics.scale

class ImageProcessorImpl : ImageProcessor {
    override fun cropCenterSquare(bitmap: Bitmap): Bitmap {
        val size = minOf(bitmap.width, bitmap.height)
        val side = (size * 0.5f).toInt()
        val left = (bitmap.width - side) / 2
        val top = (bitmap.height - side) / 2
        return Bitmap.createBitmap(bitmap, left, top, side, side)
    }

    override fun computeAverageColor(bitmap: Bitmap): Color {
        var red = 0L
        var green = 0L
        var blue = 0L
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (pixel in pixels) {
            red += AndroidColor.red(pixel)
            green += AndroidColor.green(pixel)
            blue += AndroidColor.blue(pixel)
        }
        val count = bitmap.width * bitmap.height
        return Color(
            (red / count).toInt(),
            (green / count).toInt(),
            (blue / count).toInt()
        )
    }

    override fun scaleBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return bitmap.scale(width, height)
    }
}