package com.dsds11s.saa.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Screen-scoped colors pulled from Figma node 6885:8990/8992.
private val CountdownBorderColor = Color(0xFFFFEA9E) // #FFEA9E — Details-Text-Primary-1
private val CountdownBoxTopColor = Color.White // gradient top stop
private val CountdownBoxBottomColor = Color(0x1AFFFFFF) // rgba(255,255,255,0.10)
private val CountdownLabelColor = Color.White // DAYS/HOURS/MINUTES/SECONDS label
private val CountdownDigitColor = Color.White // digit text color

/**
 * Stateless countdown timer component.
 * Renders 4 labelled digit boxes: DAYS / HOURS / MINUTES / SECONDS.
 * Sourced from Figma nodes 6885:8988–6885:9015 (countdown frame, 16dp gap).
 * Each unit is a column: two 32x56dp glass boxes + 24dp label below.
 * The orchestrator owns the ticking state and passes [days], [hours], [minutes], [seconds].
 */
@Composable
fun CountdownTimer(
    modifier: Modifier = Modifier,
    days: Int,
    hours: Int,
    minutes: Int,
    @Suppress("UNUSED_PARAMETER") seconds: Int,
    isEnded: Boolean = false,
) {
    if (isEnded) {
        // Event has passed — show an ended message instead of a frozen 00/00/00.
        Text(
            modifier = modifier,
            text = stringResource(R.string.home_countdown_ended),
            color = CountdownBorderColor,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.5.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        return
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        // Figma shows three units only: DAYS / HOURS / MINUTES (no SECONDS box).
        CountdownUnit(value = days, label = stringResource(R.string.home_countdown_label_days))
        CountdownUnit(value = hours, label = stringResource(R.string.home_countdown_label_hours))
        CountdownUnit(value = minutes, label = stringResource(R.string.home_countdown_label_minutes))
    }
}

/**
 * Single countdown unit: two digit boxes (tens + ones) above a label.
 * Box: 32x56dp, 8dp corner radius, 0.5dp #FFEA9E border, glass gradient bg.
 * Digit: 32sp "Digital Numbers" style rendered as Montserrat (font not bundled —
 * uses Montserrat Bold as closest available match for the monospace digit aesthetic).
 * Label: 18sp, FontWeight.Normal, white.
 */
@Composable
private fun CountdownUnit(
    value: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    val clamped = value.coerceIn(0, 99)
    val tens = clamped / 10
    val ones = clamped % 10
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CountdownDigitBox(digit = tens)
            CountdownDigitBox(digit = ones)
        }
        Text(
            text = label,
            color = CountdownLabelColor,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
    }
}

/**
 * Single digit glass box: 32x56dp, rounded 8dp, frosted border + vertical gradient.
 * Figma node 6885:8992: border 0.5px #FFEA9E, gradient white→white-10%, blur effect
 * approximated via Brush (Compose has no native BackdropFilter in stable APIs).
 */
@Composable
private fun CountdownDigitBox(
    digit: Int,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier =
            modifier
                .size(width = 32.dp, height = 56.dp)
                .clip(shape)
                .background(
                    brush =
                        Brush.verticalGradient(
                            0f to CountdownBoxTopColor.copy(alpha = 0.5f),
                            1f to CountdownBoxBottomColor,
                        ),
                )
                .border(
                    width = 0.5.dp,
                    color = CountdownBorderColor,
                    shape = shape,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = digit.toString(),
            color = CountdownDigitColor,
            fontSize = 32.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            lineHeight = 36.sp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A)
@Composable
private fun CountdownTimerPreview() {
    SAATheme {
        CountdownTimer(days = 20, hours = 14, minutes = 35, seconds = 8)
    }
}
