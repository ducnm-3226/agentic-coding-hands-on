package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — top nav 6885:9890, action bar 6885:10002
private val TopBarTitleColor = Color(0xFFFFFFFF) // rgba(255,255,255,1)
private val CancelBtnBorder = Color(0xFF998C5F) // var(--Colors-Boder, #998C5F)
private val CancelBtnBg = Color(0x1AFFEA9E) // rgba(255,234,158,0.10)
private val CancelBtnText = Color(0xFFFFFFFF)
private val SendBtnBg = Color(0xFFFFEA9E) // rgba(255,234,158,1) — gold fill
private val SendBtnText = Color(0xFF00101A)

/**
 * Top navigation bar for the New Kudo screen.
 * Figma node 6885:9890: back chevron (left) + centered "New Kudo" title, height 42dp.
 * Status bar padding is applied by the parent Column via statusBarsPadding().
 */
@Composable
internal fun NewKudoTopBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(42.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.kudos_ic_back),
            contentDescription = stringResource(R.string.kudos_back_content_desc),
            modifier =
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 7.dp)
                    .size(24.dp)
                    .clickable(onClick = onBack),
            colorFilter = ColorFilter.tint(Color.White),
        )
        Text(
            text = stringResource(R.string.newkudo_top_bar_title),
            color = TopBarTitleColor,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

/**
 * Bottom action bar: "Huỷ" (dark bordered) + "Gửi đi" (gold filled).
 * Figma node 6885:10002: gap 16dp, buttons 40dp tall, Huỷ flex-1 / Gửi đi 160dp.
 */
@Composable
internal fun NewKudoActionBar(
    onCancel: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Huỷ button
        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(CancelBtnBg)
                    .border(1.dp, CancelBtnBorder, RoundedCornerShape(4.dp))
                    .clickable(onClick = onCancel)
                    .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.newkudo_btn_cancel),
                color = CancelBtnText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 8.dp),
            )
            Image(
                painter = painterResource(R.drawable.kudos_ic_close),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(CancelBtnText),
            )
        }

        // Gửi đi button
        Row(
            modifier =
                Modifier
                    .size(width = 160.dp, height = 40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SendBtnBg)
                    .clickable(onClick = onSend)
                    .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.newkudo_btn_send),
                color = SendBtnText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 8.dp),
            )
            Image(
                painter = painterResource(R.drawable.kudos_ic_send),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(SendBtnText),
            )
        }
    }
}
