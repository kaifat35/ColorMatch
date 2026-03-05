package com.efimov.colormatch.data.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import com.efimov.colormatch.data.db.HistoryEntity
import com.efimov.colormatch.domain.model.HistoryEntry
import java.io.ByteArrayOutputStream

object HistoryEntryMapper {
    fun toEntity(entry: HistoryEntry): HistoryEntity {
        val stream = ByteArrayOutputStream()
        entry.bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val blob = stream.toByteArray()
        return HistoryEntity(
            id = entry.id,
            imageBlob = blob,
            colorRed = entry.color.red,
            colorGreen = entry.color.green,
            colorBlue = entry.color.blue,
            name = entry.name,
            rgb = entry.rgb,
            timestamp = entry.timestamp
        )
    }

    fun fromEntity(entity: HistoryEntity): HistoryEntry {
        val bitmap = BitmapFactory.decodeByteArray(entity.imageBlob, 0, entity.imageBlob.size)
        val color = Color(entity.colorRed, entity.colorGreen, entity.colorBlue)
        return HistoryEntry(
            id = entity.id,
            bitmap = bitmap,
            color = color,
            name = entity.name,
            rgb = entity.rgb,
            timestamp = entity.timestamp
        )
    }
}