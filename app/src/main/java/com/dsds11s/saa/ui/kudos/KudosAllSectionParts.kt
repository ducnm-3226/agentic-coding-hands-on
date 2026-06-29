package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma mms_C_All kudos (6885:9220), mms_D.3 (6885:9255)
private val ContainerBg = Color(0xFF00070C)
private val ContainerBorder = Color(0xFF998C5F)
private val RecipientTitleColor = Color(0xFFFFEA9E)
private val RecipientNameColor = Color(0xFFFFEA9E)
private val RecipientInfoColor = Color(0xFFFFFFFF)
private val AvatarBorder = Color(0xFFFFFFFF)
private val ViewAllTextColor = Color(0xFFFFFFFF)

/**
 * Top-10 gift recipients — Figma mms_D.3_10 SUNNER nhận quà (6885:9255).
 * Dark container, title "10 SUNNER NHẬN QUÀ MỚI NHẤT", then recipient rows.
 */
@Composable
internal fun TopRecipientsBlock(
    recipients: List<GiftRecipient>,
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.kudos_recipients_block_title),
            color = RecipientTitleColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
        )
        recipients.take(10).forEach { recipient ->
            RecipientRow(recipient = recipient)
        }
    }
}

/** Single recipient row — Figma node mms_D.3.2 (6885:9259). */
@Composable
internal fun RecipientRow(
    recipient: GiftRecipient,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E3940))
                    .border(1.5.dp, AvatarBorder, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = recipient.name.firstOrNull()?.toString() ?: "?",
                color = Color(0xFFFFEA9E),
                fontSize = 12.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = recipient.name,
                color = RecipientNameColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = recipient.giftDescription,
                color = RecipientInfoColor,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/** "View all Kudos →" link — Figma node 6891:15987. */
@Composable
internal fun ViewAllLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.kudos_view_all_link),
            color = ViewAllTextColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        Icon(
            painter = painterResource(R.drawable.kudos_ic_arrow_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = ViewAllTextColor,
        )
    }
}
