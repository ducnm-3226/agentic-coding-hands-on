package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma node mms_A.1_Button ghi nhận (6885:9083).
private val SendBarBorder = Color(0xFF998C5F) // var(--Details-Border)
private val SendBarBg = Color(0x1AFFEA9E) // rgba(255,234,158,0.10)
private val SendBarText = Color(0xFFFFFFFF)

/**
 * Send-Kudos bar pill — Figma node mms_A.1_Button ghi nhận (6885:9083).
 * Border #998C5F 1dp, bg rgba(255,234,158,0.10), rounded 4dp, height 40dp.
 * Pencil icon (24dp) + prompt text. Shared by the Kudos feed and the other-user profile.
 *
 * @param text   prompt label (e.g. "Hôm nay, bạn muốn gửi kudos đến ai?" or "Gửi lời cảm ơn … tới …")
 * @param onClick bar tap
 */
@Composable
fun KudosSendBar(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(SendBarBg)
                .border(1.dp, SendBarBorder, RoundedCornerShape(4.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.kudos_ic_pen),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = text,
            color = SendBarText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
    }
}
