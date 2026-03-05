package com.efimov.colormatch.domain.usercase

import android.graphics.Bitmap
import com.efimov.colormatch.domain.model.ProcessedImageResult
import com.efimov.colormatch.domain.utils.X11Palette
import javax.inject.Inject

class ProcessImageUseCase @Inject constructor(
    private val imageProcessor: ImageProcessor,
    private val getClosestColor: GetClosestColorUseCase
) {
    operator fun invoke(originalBitmap: Bitmap): ProcessedImageResult {
        val cropped = imageProcessor.cropCenterSquare(originalBitmap)
        val avgColor = imageProcessor.computeAverageColor(cropped)
        val thumbnail = imageProcessor.scaleBitmap(cropped, 200, 200)
        val closest = getClosestColor(avgColor)
        val x11Color = X11Palette.findClosest(avgColor)
        val colorName = x11Color.name
        val rgbHex = String.format(
            "#%02X%02X%02X",
            (avgColor.red * 255).toInt(),
            (avgColor.green * 255).toInt(),
            (avgColor.blue * 255).toInt()
        )
        return ProcessedImageResult(thumbnail, avgColor, closest,colorName, rgbHex)
    }
}
