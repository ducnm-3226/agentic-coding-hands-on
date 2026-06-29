package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens re-used from KudosCard — private within the kudos package
internal val KudosCardBorder = Color(0xFFFFEA9E)
private val TimestampColor = Color(0xFF999999)
private val BadgeColor = Color(0xFF00101A)
private val HashtagRed = Color(0xFFE73928)

/** Sender → receiver row — node I6885:9092;89:2950 (gap 8dp, height 62dp). */
@Composable
internal fun KudosTransferRow(
    post: KudosPost,
    modifier: Modifier = Modifier,
    onPersonClick: ((name: String) -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KudosPersonInfo(
            name = post.senderName,
            label = stringResource(R.string.kudos_sender_label),
            onClick = onPersonClick?.let { { it(post.senderName) } },
        )
        Icon(
            painter = painterResource(R.drawable.kudos_ic_send),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color(0xFF00101A),
        )
        KudosPersonInfo(
            name = post.receiverName,
            label = stringResource(R.string.kudos_receiver_label),
            onClick = onPersonClick?.let { { it(post.receiverName) } },
        )
    }
}

/** Single person info card (avatar + name) — node 6885:8347. Tappable when [onClick] is set. */
@Composable
internal fun KudosPersonInfo(
    name: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E3940))
                    .border(1.5.dp, KudosCardBorder, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name.firstOrNull()?.toString() ?: "?",
                color = KudosCardBorder,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = name,
            color = Color(0xFF00101A),
            fontSize = 10.sp,
            lineHeight = 14.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Content block — timestamp, badge label, message body, hashtags. */
@Composable
internal fun KudosContentBlock(
    post: KudosPost,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = post.timestamp,
            color = TimestampColor,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.23.sp,
        )
        Text(
            text = post.badgeLabel,
            color = BadgeColor,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.23.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = post.message,
            color = Color(0xFF00101A),
            fontSize = 12.sp,
            lineHeight = 17.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        if (post.hashtags.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                post.hashtags.take(5).forEach { tag ->
                    Text(
                        text = tag,
                        color = HashtagRed,
                        fontSize = 10.sp,
                        lineHeight = 11.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}
