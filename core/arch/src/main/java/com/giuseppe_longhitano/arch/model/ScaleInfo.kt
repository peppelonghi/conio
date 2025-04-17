package com.giuseppe_longhitano.arch.model

import kotlin.math.abs
import kotlin.math.log10

private const val SUFFIX_B = "B"
private const val SUFFIX_M = "M"
private const val SUFFIX_K = "K"
private const val SUFFIX_MICRO = "µ"
private const val SUFFIX_MILLIS = "m"
private const val SCALE_B = 1_000_000_000.0
private const val SCALE_M = 1_000_000.0
private const val SCALE_K = 1_000.0
private const val SCALE_MICRO = 0.000001
private const val SCALE_MILLIS = 0.001

private const val MIN_MULTIPLIER = 3


sealed class ScaleInfo(val scale: Double, val suffix: String) {
    data object NoScale: ScaleInfo(scale = 1.0, suffix = "")
    data object Bilions: ScaleInfo(scale =SCALE_B, suffix =  SUFFIX_B)
    data object Millions: ScaleInfo(scale= SCALE_M, suffix=  SUFFIX_M)
    data object Thousands: ScaleInfo(scale =SCALE_K, suffix=  SUFFIX_K)
    data object Micro: ScaleInfo(scale =SCALE_MICRO, suffix = SUFFIX_MICRO)
    data object Millis: ScaleInfo(scale =SCALE_MILLIS, suffix = SUFFIX_MILLIS)


    companion object{
        fun determineScale(data: List<Double>): ScaleInfo {
            if (data.isEmpty()) return NoScale
            val maxValue = data.maxOf { abs(it) }
            val magnitude = if (maxValue == 0.0) 0 else log10(maxValue).toInt()
            return when {
                magnitude >= MIN_MULTIPLIER.times(3) -> Bilions
                magnitude >= MIN_MULTIPLIER.times(2) -> Millions
                magnitude >= MIN_MULTIPLIER -> Thousands
                magnitude <= -MIN_MULTIPLIER -> {
                    // Find the smallest non-zero magnitude
                    val smallestMagnitude = data.filter { it != 0.0 }.minOfOrNull { abs(it) }
                        ?.let { log10(it).toInt() } ?: 0
                    when {
                        smallestMagnitude <= -MIN_MULTIPLIER.times(2) -> Micro
                        smallestMagnitude <= -MIN_MULTIPLIER -> Millis
                        else -> NoScale
                    }
                }

                else -> NoScale
            }
        }

    }

}

