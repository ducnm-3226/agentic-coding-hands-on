package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.ui.theme.Montserrat

// Shared design tokens + content for the Community Standards page.
// Figma [iOS] Sun*Kudos_Tiêu chuẩn cộng đồng (6885:10806).
internal val CsScreenBg = Color(0xFF00101A)
internal val CsGold = Color(0xFFFFEA9E) // gold section headings + emphasis
internal val CsBody = Color(0xFFFFFFFF) // paragraphs + list items
internal val CsMuted = Color(0xFFCCCCCC) // intro warning line
internal val CsDivider = Color(0xFF2E3940)

/** Gold section title — "Tiêu chuẩn cộng đồng" / "Tiêu chuẩn bảo mật". */
@Composable
internal fun SectionHeading(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = CsGold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}

/** Numbered criterion row — index + body, matching the design's ordered list. */
@Composable
internal fun NumberedItem(
    number: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "$number.",
            color = CsBody,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        Text(
            text = text,
            color = CsBody,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
        )
    }
}

/** Bullet row — leading dot + body. */
@Composable
internal fun BulletItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "•",
            color = CsBody,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
        )
        Text(
            text = text,
            color = CsBody,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
        )
    }
}
