package com.efimov.colormatch.domain.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.pow

data class X11Color(val name: String, val color: Color)

object X11Palette {
    val colors = listOf(
        X11Color("Красный", Color(0xFFF44336)),
        X11Color("Тёмно-красный", Color(0xFF8B0000)),
        X11Color("Светло-коралловый", Color(0xFFF08080)),
        X11Color("Индийский красный", Color(0xFFCD5C5C)),
        X11Color("Малиновый", Color(0xFFDC143C)),
        X11Color("Кирпичный", Color(0xFFB22222)),
        X11Color("Оранжевый", Color(0xFFFFA500)),
        X11Color("Тёмно-оранжевый", Color(0xFFFF8C00)),
        X11Color("Золотой", Color(0xFFFFD700)),
        X11Color("Жёлтый", Color(0xFFFFFF00)),
        X11Color("Светло-жёлтый", Color(0xFFFFFFE0)),
        X11Color("Оливковый", Color(0xFF808000)),
        X11Color("Зелёный", Color(0xFF00FF00)),
        X11Color("Тёмно-зелёный", Color(0xFF006400)),
        X11Color("Светло-зелёный", Color(0xFF90EE90)),
        X11Color("Лаймовый", Color(0xFF00FF00)),
        X11Color("Изумрудный", Color(0xFF50C878)),
        X11Color("Голубой", Color(0xFF00FFFF)),
        X11Color("Тёмно-голубой", Color(0xFF00CED1)),
        X11Color("Светло-голубой", Color(0xFFE0FFFF)),
        X11Color("Синий", Color(0xFF0000FF)),
        X11Color("Тёмно-синий", Color(0xFF00008B)),
        X11Color("Светло-синий", Color(0xFFADD8E6)),
        X11Color("Фиолетовый", Color(0xFFEE82EE)),
        X11Color("Тёмно-фиолетовый", Color(0xFF9400D3)),
        X11Color("Светло-фиолетовый", Color(0xFFD8BFD8)),
        X11Color("Пурпурный", Color(0xFFFF00FF)),
        X11Color("Розовый", Color(0xFFFFC0CB)),
        X11Color("Тёмно-розовый", Color(0xFFFF1493)),
        X11Color("Светло-розовый", Color(0xFFFFB6C1)),
        X11Color("Коричневый", Color(0xFFA52A2A)),
        X11Color("Тёмно-коричневый", Color(0xFF5D3A1A)),
        X11Color("Светло-коричневый", Color(0xFFD2B48C)),
        X11Color("Бежевый", Color(0xFFF5F5DC)),
        X11Color("Серый", Color(0xFF808080)),
        X11Color("Тёмно-серый", Color(0xFF404040)),
        X11Color("Светло-серый", Color(0xFFD3D3D3)),
        X11Color("Чёрный", Color(0xFF000000)),
        X11Color("Белый", Color(0xFFFFFFFF))
    )

    private val labColors: List<FloatArray> = colors.map { rgbToLab(it.color) }

    fun findClosest(target: Color): X11Color {
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

    private fun rgbToLab(color: Color): FloatArray {
        val r = color.red.toDouble()
        val g = color.green.toDouble()
        val b = color.blue.toDouble()

        fun linearize(c: Double): Double =
            if (c > 0.04045) ((c + 0.055) / 1.055).pow(2.4) else c / 12.92

        val rLin = linearize(r)
        val gLin = linearize(g)
        val bLin = linearize(b)

        val x = rLin * 0.4124564 + gLin * 0.3575761 + bLin * 0.1804375
        val y = rLin * 0.2126729 + gLin * 0.7151522 + bLin * 0.0721750
        val z = rLin * 0.0193339 + gLin * 0.1191920 + bLin * 0.9503041

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