package com.xasdify.pocketflow.utils

import kotlin.time.ExperimentalTime

/**
 * Format a double to 2 decimal places
 * Kotlin Multiplatform compatible
 */
fun Double.formatCurrency(): String {
    val rounded = (this * 100).toLong() / 100.0
    val intPart = rounded.toLong()
    val decimalPart = ((rounded - intPart) * 100).toLong()
    return "$intPart.${decimalPart.toString().padStart(2, '0')}"
}

/**
 * Get current time in milliseconds
 * Kotlin Multiplatform compatible
 */
@OptIn(ExperimentalTime::class)
fun getCurrentTimeMilli(): Long {
    return kotlin.time.Clock.System.now().toEpochMilliseconds()
}
