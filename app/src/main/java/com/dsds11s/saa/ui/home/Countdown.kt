package com.dsds11s.saa.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.TimeZone

/**
 * Remaining time broken into display units.
 * [isEnded] is true once the target date has elapsed (all units clamped to 0),
 * so the UI can show an "event ended" state instead of a silent 00/00/00.
 */
data class Countdown(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val isEnded: Boolean,
)

/**
 * SAA event date the countdown targets (26/12/2026, local time).
 * Single source of truth — change this to retarget the countdown.
 * Must stay in the future, otherwise the timer renders the ended state.
 * Calendar months are 0-based (DECEMBER == 11).
 */
val EVENT_TIME_MILLIS: Long =
    Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).apply {
        clear()

        set(2026, Calendar.DECEMBER, 26, 0, 0, 0)
    }.timeInMillis

/**
 * Live countdown to [targetMillis], recomposing every second.
 * Once the target has elapsed the values clamp to zero and [Countdown.isEnded] is true.
 */
@Composable
fun rememberCountdown(targetMillis: Long = EVENT_TIME_MILLIS): Countdown {
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(targetMillis) {
        while (true) {
            delay(1000L)
            now = System.currentTimeMillis()
        }
    }
    return countdownFrom(targetMillis - now)
}

/**
 * Pure breakdown of [remainingMillis] into a [Countdown]. Negative/zero remaining
 * clamps every unit to 0 and marks [Countdown.isEnded] = true. Extracted so the
 * core logic is unit-testable without a Composable/clock.
 */
fun countdownFrom(remainingMillis: Long): Countdown {
    val totalSeconds = remainingMillis.coerceAtLeast(0L) / 1000L
    return Countdown(
        days = (totalSeconds / 86_400L).toInt(),
        hours = ((totalSeconds % 86_400L) / 3_600L).toInt(),
        minutes = ((totalSeconds % 3_600L) / 60L).toInt(),
        seconds = (totalSeconds % 60L).toInt(),
        isEnded = remainingMillis <= 0L,
    )
}
