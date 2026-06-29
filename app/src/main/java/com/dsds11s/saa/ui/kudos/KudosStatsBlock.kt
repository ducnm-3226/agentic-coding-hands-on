package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma mms_D.1_Thống kê tổng quát (6885:9223)
private val ContainerBg = Color(0xFF00070C) // var(--Details-Container-2, #00070C)
private val ContainerBorder = Color(0xFF998C5F) // var(--Details-Border)
private val LabelColor = Color(0xFFFFFFFF) // stat label
private val ValueColor = Color(0xFFFFEA9E) // stat value (gold)
private val DividerColor = Color(0xFF2E3940) // mms_D.1.5 rgba(46,57,64,1)
private val ButtonBg = Color(0xFFFFEA9E) // "Mở Secret Box" button
private val ButtonText = Color(0xFF00101A)

/**
 * Personal stats block + "Mở Secret Box" button — Figma mms_D.1 (6885:9223).
 * Dark container (#00070C), border #998C5F, rounded 8dp, padding 12dp.
 * Stats rows: kudos received/given, hearts received (x2 multiplier), secret box opened/unopened.
 * Divider between hearts and secret box rows.
 * "Mở Secret Box" filled gold button at bottom.
 */
@Composable
fun KudosStatsBlock(
    stats: KudosStats,
    onOpenSecretBox: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ContainerBg)
                .border(1.dp, ContainerBorder, RoundedCornerShape(8.dp))
                .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Stats rows — node 6885:9224 (Nội dung)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // D.1.2 Số kudos nhận được — node 6885:9225
            StatRow(
                label = stringResource(R.string.kudos_stat_received),
                value = stats.kudosReceived.toString(),
            )
            // D.1.3 Số kudos đã gửi — node 6885:9230
            StatRow(
                label = stringResource(R.string.kudos_stat_given),
                value = stats.kudosGiven.toString(),
            )
            // D.1.4 Số tim — node 6885:9235 (includes x2 badge)
            StatRow(
                label = stringResource(R.string.kudos_stat_hearts_received),
                value = stats.heartsReceived.toString(),
                suffix = "x2",
            )
            // Divider — node 6885:9243
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DividerColor),
            )
            // D.1.6 Secret box đã mở — node 6885:9244
            StatRow(
                label = stringResource(R.string.kudos_stat_secret_box_opened),
                value = stats.secretBoxOpened.toString(),
            )
            // D.1.7 Secret box chưa mở — node 6885:9249
            StatRow(
                label = stringResource(R.string.kudos_stat_secret_box_unopened),
                value = stats.secretBoxUnopened.toString(),
            )
        }

        // "Mở Secret Box" button — node 6885:9254, bg #FFEA9E, text #00101A
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(ButtonBg)
                    .clickable(onClick = onOpenSecretBox),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.kudos_open_secret_box),
                    color = ButtonText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                )
                // Gift box icon at the end (node 6885:9254)
                Icon(
                    painter = painterResource(R.drawable.kudos_ic_gift),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = ButtonText,
                )
            }
        }
    }
}

/**
 * Single stat row: label left, optional suffix badge + value right.
 * Node pattern: mms_D.1.2 / mms_D.1.3 / mms_D.1.4 etc.
 */
@Composable
private fun StatRow(
    label: String,
    value: String,
    suffix: String? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            color = LabelColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Light,
            letterSpacing = 0.25.sp,
            modifier = Modifier.weight(1f),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // x2 multiplier badge — flame icon with "x2" overlaid (node 6885:9239)
            if (suffix != null) {
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.kudos_ic_flame),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = suffix,
                        color = Color(0xFFFFFFFF),
                        fontSize = 9.sp,
                        lineHeight = 9.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
            Text(
                text = value,
                color = ValueColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
            )
        }
    }
}
