package com.efimov.colormatch.domain.usercase

import com.efimov.colormatch.domain.model.HistoryEntry
import com.efimov.colormatch.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(): Flow<List<HistoryEntry>> = repository.getAllEntries()
}