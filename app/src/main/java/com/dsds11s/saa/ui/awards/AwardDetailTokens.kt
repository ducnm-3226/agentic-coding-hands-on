package com.dsds11s.saa.ui.awards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Shared tokens — used by AwardDetailContent and AwardDetailWidgets
internal val AwardIconTint = Color(0xFFFFEA9E)
internal val AwardLabelColor = Color(0xFFFFFFFF)
internal val AwardValueColor = Color(0xFFFFEA9E)
internal val AwardUnitColor = Color(0xFFFFFFFF)
internal val AwardTitleTextColor = Color(0xFFFFEA9E)

/**
 * Award artwork drawable, reusing the Home awards-section images so the trophy on the
 * detail screen matches the card the user tapped. Returns null for awards Home has no
 * image for (→ procedural placeholder).
 */
@DrawableRes
internal fun awardTrophyRes(id: String): Int? =
    when (id) {
        "top-talent" -> R.drawable.home_award_card1_bg
        "top-project" -> R.drawable.home_award_card2_bg
        "top-project-leader" -> R.drawable.home_award_card3_bg
        else -> null
    }

/** Short initials label keyed to each award id — differentiates placeholders visually. */
internal fun awardInitials(id: String): String =
    when (id) {
        "signature-creator" -> "S"
        "mvp" -> "M"
        "top-talent" -> "TT"
        "top-project" -> "TP"
        "top-project-leader" -> "PL"
        "best-manager" -> "BM"
        else -> "★"
    }

/** Award title row: target icon (24dp gold) + award name text (18sp gold Medium). */
@Composable
internal fun AwardTitleRow(
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.award_ic_target),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(AwardIconTint),
        )
        Text(
            text = name,
            color = AwardTitleTextColor,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp,
        )
    }
}

/**
 * Stat row: icon + label / value (gold, 18sp) / note (white, 12sp).
 * Used for both quantity and prize rows.
 */
@Composable
internal fun AwardStatRow(
    iconRes: Int,
    label: String,
    value: String,
    note: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier =
                Modifier
                    .size(20.dp)
                    .padding(top = 2.dp),
            colorFilter = ColorFilter.tint(AwardIconTint),
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = label,
                color = AwardLabelColor,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = value,
                color = AwardValueColor,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp,
            )
            Text(
                text = note,
                color = AwardUnitColor,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Light,
            )
        }
    }
}
