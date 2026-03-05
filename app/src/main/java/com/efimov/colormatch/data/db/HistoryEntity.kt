package com.efimov.colormatch.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey val id: Long,
    val imageBlob: ByteArray,
    val colorRed: Float,
    val colorGreen: Float,
    val colorBlue: Float,
    val name: String,
    val rgb: String,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HistoryEntity

        if (id != other.id) return false
        if (!imageBlob.contentEquals(other.imageBlob)) return false
        if (colorRed != other.colorRed) return false
        if (colorGreen != other.colorGreen) return false
        if (colorBlue != other.colorBlue) return false
        if (name != other.name) return false
        if (rgb != other.rgb) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + imageBlob.contentHashCode()
        result = 31 * result + colorRed.hashCode()
        result = 31 * result + colorGreen.hashCode()
        result = 31 * result + colorBlue.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + rgb.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}