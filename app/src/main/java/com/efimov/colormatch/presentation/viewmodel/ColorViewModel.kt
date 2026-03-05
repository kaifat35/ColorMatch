package com.efimov.colormatch.presentation.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.colormatch.domain.model.HarmonyColors
import com.efimov.colormatch.domain.model.HistoryEntry
import com.efimov.colormatch.domain.model.ProcessedImageResult
import com.efimov.colormatch.domain.usercase.AddHistoryEntryUseCase
import com.efimov.colormatch.domain.usercase.ClearHistoryUseCase
import com.efimov.colormatch.domain.usercase.ComputeHarmoniesUseCase
import com.efimov.colormatch.domain.usercase.DeleteHistoryEntryUseCase
import com.efimov.colormatch.domain.usercase.GetHistoryUseCase
import com.efimov.colormatch.domain.usercase.ProcessImageUseCase
import com.efimov.colormatch.domain.utils.PaletteColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ColorViewModel @Inject constructor(
    private val processImageUseCase: ProcessImageUseCase,
    private val addHistoryUseCase: AddHistoryEntryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryEntryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val computeHarmoniesUseCase: ComputeHarmoniesUseCase
) : ViewModel() {

    private val _currentResult = MutableStateFlow<ProcessedImageResult?>(null)
    val currentResult: StateFlow<ProcessedImageResult?> = _currentResult

    private val _history = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val history: StateFlow<List<HistoryEntry>> = _history

    private val _harmonies = MutableStateFlow<HarmonyColors?>(null)
    val harmonies: StateFlow<HarmonyColors?> = _harmonies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadHistory()
    }

    fun processCapturedBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = processImageUseCase(bitmap)
                _currentResult.value = result
                _harmonies.value = computeHarmoniesUseCase(result.averageColor)
            } catch (e: Exception) {
                _error.value = "Ошибка обработки: ${e.localizedMessage ?: "неизвестная ошибка"}"
                Log.e("ColorViewModel", "Image processing failed", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addCurrentToHistory() {
        val result = _currentResult.value ?: return
        viewModelScope.launch {
            val entry = HistoryEntry(
                bitmap = result.thumbnail,
                color = result.averageColor,
                name = result.closestPaletteColor.name,
                rgb = result.rgbHex
            )
            addHistoryUseCase(entry)
            loadHistory()
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            getHistoryUseCase().collectLatest { list ->
                _history.value = list
            }
        }
    }

    fun deleteHistoryEntry(entry: HistoryEntry) {
        viewModelScope.launch {
            deleteHistoryUseCase(entry)
            loadHistory()
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            clearHistoryUseCase()
            loadHistory()
        }
    }

    fun loadEntryForResult(entry: HistoryEntry) {
        val result = ProcessedImageResult(
            thumbnail = entry.bitmap,
            averageColor = entry.color,
            closestPaletteColor = PaletteColor(entry.name, entry.color),
            rgbHex = entry.rgb
        )
        _currentResult.value = result
        _harmonies.value = computeHarmoniesUseCase(entry.color)
    }
}