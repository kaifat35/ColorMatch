package com.efimov.colormatch.domain.usercase

import android.graphics.Bitmap
import com.efimov.colormatch.domain.model.ProcessedImageResult
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
        val rgbHex = String.format(
            "#%02X%02X%02X",
            (avgColor.red * 255).toInt(),
            (avgColor.green * 255).toInt(),
            (avgColor.blue * 255).toInt()
        )
        return ProcessedImageResult(thumbnail, avgColor, closest, rgbHex)
    }
}
