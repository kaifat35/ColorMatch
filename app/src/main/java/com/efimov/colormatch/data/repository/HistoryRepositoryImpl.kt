package com.efimov.colormatch.data.repository

import com.efimov.colormatch.data.db.HistoryDao
import com.efimov.colormatch.data.mapper.HistoryEntryMapper
import com.efimov.colormatch.domain.model.HistoryEntry
import com.efimov.colormatch.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(
    private val dao: HistoryDao
) : HistoryRepository {
    override suspend fun addEntry(entry: HistoryEntry) {
        val entity = HistoryEntryMapper.toEntity(entry)
        dao.insert(entity)
    }

    override fun getAllEntries(): Flow<List<HistoryEntry>> =
        dao.getAll().map { entities ->
            entities.map { HistoryEntryMapper.fromEntity(it) }
        }

    override suspend fun deleteEntry(entry: HistoryEntry) {
        dao.deleteById(entry.id)
    }

    override suspend fun clearAll() {
        dao.clearAll()
    }
}