package com.dsds11s.saa

import com.dsds11s.saa.ui.home.EVENT_TIME_MILLIS
import com.dsds11s.saa.ui.home.countdownFrom
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Regression tests for the home countdown.
 *
 * Root cause of the "countdown not working" defect: the hardcoded event date was
 * in the past, so remaining time clamped to 0 and the timer rendered a frozen
 * 00/00/00. These tests guard both the breakdown math and the live event target.
 */
class CountdownTest {
    @Test
    fun breakdownSplitsRemainingTimeIntoUnits() {
        // 2 days, 3 hours, 4 minutes, 5 seconds
        val remaining = ((2L * 86_400) + (3L * 3_600) + (4L * 60) + 5L) * 1000L
        val c = countdownFrom(remaining)
        assertEquals(2, c.days)
        assertEquals(3, c.hours)
        assertEquals(4, c.minutes)
        assertEquals(5, c.seconds)
        assertFalse(c.isEnded)
    }

    @Test
    fun pastTargetClampsToZeroAndMarksEnded() {
        val c = countdownFrom(-1_000L)
        assertEquals(0, c.days)
        assertEquals(0, c.hours)
        assertEquals(0, c.minutes)
        assertEquals(0, c.seconds)
        assertTrue("Elapsed countdown must report isEnded", c.isEnded)
    }

    @Test
    fun zeroRemainingIsEnded() {
        assertTrue(countdownFrom(0L).isEnded)
    }

    /**
     * Directly catches the original defect: the event target must be in the future,
     * otherwise the live countdown freezes at 00/00/00. Fails with a past date,
     * and serves as a reminder to retarget once 26/12/2026 lapses.
     */
    @Test
    fun eventTargetIsInTheFuture() {
        assertTrue(
            "EVENT_TIME_MILLIS must be in the future or the countdown shows a frozen 00/00/00",
            EVENT_TIME_MILLIS > System.currentTimeMillis(),
        )
    }
}
