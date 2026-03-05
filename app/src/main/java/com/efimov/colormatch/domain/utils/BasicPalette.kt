package com.efimov.colormatch.domain.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.pow

data class PaletteColor(val name: String, val color: Color)

object BasicPalette {
    val colors = listOf(
        PaletteColor("Красный", Color(0xFFF44336)),
        PaletteColor("Оранжевый", Color(0xFFFF9800)),
        PaletteColor("Жёлтый", Color(0xFFFFEB3B)),
        PaletteColor("Зелёный", Color(0xFF4CAF50)),
        PaletteColor("Голубой", Color(0xFF03A9F4)),
        PaletteColor("Синий", Color(0xFF2196F3)),
        PaletteColor("Фиолетовый", Color(0xFF9C27B0)),
        PaletteColor("Розовый", Color(0xFFE91E63)),
        PaletteColor("Коричневый", Color(0xFF795548)),
        PaletteColor("Серый", Color(0xFF9E9E9E)),
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