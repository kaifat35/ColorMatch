package com.efimov.colormatch.domain.usercase

import com.efimov.colormatch.domain.repository.HistoryRepository
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke() {
        repository.clearAll()
    }
}