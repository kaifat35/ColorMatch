package com.efimov.colormatch.domain.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HistoryEntry(
    val id: Long = System.currentTimeMillis(),
    val bitmap: Bitmap,
    val color: Color,
    val name: String,
    val rgb: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    val formattedTime: String
        get() = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
}