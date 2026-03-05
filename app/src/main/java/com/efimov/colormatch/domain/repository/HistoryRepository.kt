package com.efimov.colormatch.domain.repository

import com.efimov.colormatch.domain.model.HistoryEntry
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun addEntry(entry: HistoryEntry)
    fun getAllEntries(): Flow<List<HistoryEntry>>
    suspend fun deleteEntry(entry: HistoryEntry)
    suspend fun clearAll()
}