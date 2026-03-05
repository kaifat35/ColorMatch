package com.efimov.colormatch.domain.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.pow

data class PaletteColor(val name: String, val color: Color)

object BasicPalette {
    val colors = listOf(
        PaletteColor("Красный", Color(0xFFFF0000)),
        PaletteColor("Тёмно-красный", Color(0xFF8B0000)),
        PaletteColor("Светло-красный", Color(0xFFFF6347)), // Tomato
        PaletteColor("Бордовый", Color(0xFF800000)),
        PaletteColor("Оранжевый", Color(0xFFFFA500)),
        PaletteColor("Тёмно-оранжевый", Color(0xFFFF8C00)),
        PaletteColor("Светло-оранжевый", Color(0xFFFFD700)), // Gold
        PaletteColor("Жёлтый", Color(0xFFFFFF00)),
        PaletteColor("Светло-жёлтый", Color(0xFFFFFFE0)),
        PaletteColor("Оливковый", Color(0xFF808000)),
        PaletteColor("Зелёный", Color(0xFF00FF00)),
        PaletteColor("Тёмно-зелёный", Color(0xFF006400)),
        PaletteColor("Светло-зелёный", Color(0xFF90EE90)),
        PaletteColor("Изумрудный", Color(0xFF50C878)),
        PaletteColor("Голубой", Color(0xFF00FFFF)),
        PaletteColor("Тёмно-голубой", Color(0xFF00CED1)),
        PaletteColor("Светло-голубой", Color(0xFFE0FFFF)),
        PaletteColor("Синий", Color(0xFF0000FF)),
        PaletteColor("Тёмно-синий", Color(0xFF00008B)),
        PaletteColor("Светло-синий", Color(0xFFADD8E6)),
        PaletteColor("Фиолетовый", Color(0xFFEE82EE)),
        PaletteColor("Тёмно-фиолетовый", Color(0xFF9400D3)),
        PaletteColor("Светло-фиолетовый", Color(0xFFD8BFD8)),
        PaletteColor("Пурпурный", Color(0xFFFF00FF)),
        PaletteColor("Розовый", Color(0xFFFFC0CB)),
        PaletteColor("Тёмно-розовый", Color(0xFFFF1493)),
        PaletteColor("Светло-розовый", Color(0xFFFFB6C1)),
        PaletteColor("Коричневый", Color(0xFFA52A2A)),
        PaletteColor("Тёмно-коричневый", Color(0xFF5D3A1A)),
        PaletteColor("Светло-коричневый", Color(0xFFD2B48C)),
        PaletteColor("Бежевый", Color(0xFFF5F5DC)),
        PaletteColor("Серый", Color(0xFF808080)),
        PaletteColor("Тёмно-серый", Color(0xFF404040)),
        PaletteColor("Светло-серый", Color(0xFFD3D3D3)),
        PaletteColor("Чёрный", Color(0xFF000000)),
        PaletteColor("Белый", Color(0xFFFFFFFF))
    )

    // Предвычисленные LAB-координаты для цветов палитры
    private val labColors: List<FloatArray> = colors.map { rgbToLab(it.color) }

    // Поиск ближайшего цвета по евклидову расстоянию в LAB
    fun findClosest(target: Color): PaletteColor {
        val targetLab = rgbToLab(target)
        var bestIndex = 0
        var bestDistance = Double.MAX_VALUE
        for (i in labColors.indices) {
            val dist = labDistance(targetLab, labColors[i])
            if (dist < bestDistance) {
                bestDistance = dist
                bestIndex = i
            }
        }
        return colors[bestIndex]
    }

    // Преобразование RGB (0..1) в LAB
    private fun rgbToLab(color: Color): FloatArray {
        val r = color.red.toDouble()
        val g = color.green.toDouble()
        val b = color.blue.toDouble()

        // 1. RGB to XYZ (D65 illuminant)
        fun linearize(c: Double): Double =
            if (c > 0.04045) ((c + 0.055) / 1.055).pow(2.4) else c / 12.92

        val rLin = linearize(r)
        val gLin = linearize(g)
        val bLin = linearize(b)

        val x = rLin * 0.4124564 + gLin * 0.3575761 + bLin * 0.1804375
        val y = rLin * 0.2126729 + gLin * 0.7151522 + bLin * 0.0721750
        val z = rLin * 0.0193339 + gLin * 0.1191920 + bLin * 0.9503041

        // 2. XYZ to LAB
        fun f(t: Double): Double =
            if (t > 0.008856) t.pow(1.0 / 3.0) else (7.787 * t + 16.0 / 116.0)

        val xr = x / 0.95047
        val yr = y / 1.0
        val zr = z / 1.08883

        val fx = f(xr)
        val fy = f(yr)
        val fz = f(zr)

        val L = (116.0 * fy - 16.0).toFloat()
        val A = (500.0 * (fx - fy)).toFloat()
        val B = (200.0 * (fy - fz)).toFloat()

        return floatArrayOf(L, A, B)
    }

    private fun labDistance(lab1: FloatArray, lab2: FloatArray): Double {
        val dL = lab1[0] - lab2[0]
        val dA = lab1[1] - lab2[1]
        val dB = lab1[2] - lab2[2]
        return (dL * dL + dA * dA + dB * dB).toDouble()
    }

}