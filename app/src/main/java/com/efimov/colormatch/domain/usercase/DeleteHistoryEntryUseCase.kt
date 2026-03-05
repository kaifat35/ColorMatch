package com.efimov.colormatch.domain.usercase

import com.efimov.colormatch.domain.model.HistoryEntry
import com.efimov.colormatch.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryEntryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(entry: HistoryEntry) {
        repository.deleteEntry(entry)
    }
}