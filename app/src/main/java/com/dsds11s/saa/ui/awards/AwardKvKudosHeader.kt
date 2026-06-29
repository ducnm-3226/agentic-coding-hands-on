package com.dsds11s.saa.ui.awards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Design tokens — Figma mms_A_KV Kudos (6885:10658)
private val KvEyebrowColor = Color(0xFFFFFFFF) // "Hệ thống ghi nhận và cảm ơn" fill (white, per Figma)

/**
 * Shared Kudos keyvisual banner — Figma mms_A_KV Kudos (6885:10658).
 *
 * Displays:
 *   - Eyebrow text: "Hệ thống ghi nhận và cảm ơn" (white, 14sp Medium)
 *   - The Sun* "🔥 KUDOS" brand lockup (drawable home_kudos_logo, same asset as the Home banner)
 *
 * Used by AwardDetailScreen and KudosScreen (KudosScreen.kt:126).
 * No args — purely static branding block.
 */
@Composable
fun AwardKvKudosHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Eyebrow — node 6885:10660 "Hệ thống ghi nhận và cảm ơn"
        Text(
            text = stringResource(R.string.award_kv_kudos_subtitle),
            color = KvEyebrowColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )

        // Sun* "🔥 KUDOS" brand lockup — node 6885:10662 "kudo logo".
        // Reuse the existing brand asset (flame + wordmark) instead of re-creating it from
        // a tinted icon + text, so it matches the Figma branding exactly. Aspect ≈ 118:21.
        Image(
            painter = painterResource(R.drawable.home_kudos_logo),
            contentDescription = "Sun* Kudos",
            modifier = Modifier.size(width = 180.dp, height = 32.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun AwardKvKudosHeaderPreview() {
    SAATheme {
        AwardKvKudosHeader(modifier = Modifier.padding(vertical = 16.dp))
    }
}
