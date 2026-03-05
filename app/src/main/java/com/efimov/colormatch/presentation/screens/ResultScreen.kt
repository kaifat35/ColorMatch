package com.efimov.colormatch.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.efimov.colormatch.domain.model.HarmonyColors
import com.efimov.colormatch.domain.model.ProcessedImageResult
import com.efimov.colormatch.domain.utils.PaletteColor
import com.efimov.colormatch.presentation.viewmodel.ColorViewModel

@Composable
fun ResultScreen(navController: NavController, viewModel: ColorViewModel) {
    val result by viewModel.currentResult.collectAsStateWithLifecycle()
    val harmonies by viewModel.harmonies.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    when {
        isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ошибка: $error")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Назад")
                    }
                }
            }
        }
        result == null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Нет данных")
            }
        }
        else -> {
            ResultContent(result!!, harmonies, navController, viewModel)
        }
    }
}

@Composable
fun ResultContent(
    result: ProcessedImageResult,
    harmonies: HarmonyColors?,
    navController: NavController,
    viewModel: ColorViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            bitmap = result.thumbnail.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .border(2.dp, Color.Gray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Определённый цвет:", style = MaterialTheme.typography.titleMedium)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Название: ${result.colorName}", style = MaterialTheme.typography.bodyLarge)
                Text("RGB: ${result.rgbHex}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        harmonies?.let { h ->
            Text("Гармоничные сочетания", style = MaterialTheme.typography.headlineSmall)
            HarmonyCategory("Комплементарные", h.complementary)
            HarmonyCategory("Аналогичные", h.analogous)
            HarmonyCategory("Триадные", h.triadic)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Новое фото")
            }
            Button(
                onClick = {
                    viewModel.addCurrentToHistory()
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Сохранить в историю")
            }
        }
    }
}

@Composable
fun HarmonyCategory(title: String, colors: List<PaletteColor>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colors) { paletteColor ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(paletteColor.color)
                            .border(1.dp, Color.Black)
                    )
                    Text(
                        text = paletteColor.name,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .wrapContentWidth()
                    )
                }
            }
        }
    }
}