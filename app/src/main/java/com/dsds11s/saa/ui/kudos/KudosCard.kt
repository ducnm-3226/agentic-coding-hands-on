package com.dsds11s.saa.ui.kudos

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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma node mms_B.3_KUDO Highlight (6885:8424)
private val CardBg = Color(0xFFFFF8E1) // background #FFF8E1
private val CardBorder = Color(0xFFFFEA9E) // var(--Colors-Primary, #FFEA9E)
private val CardDivider = Color(0xFFFFEA9E) // Rectangle 14/15 fill
private val ActionText = Color(0xFF00101A)

/**
 * Canonical Kudos card — Figma node mms_B.3_KUDO (6885:8424).
 *
 * The single source of truth for a Kudos list/carousel item. Used by the Highlight
 * carousel (KudosHighlightSection), the Kudos home feed (KudosAllSection) and the
 * All Kudos screen — identical layout, spacing, typography, icons and colors everywhere.
 *
 * Card: border #FFEA9E 1dp, rounded 8dp, bg #FFF8E1, padding 8dp/12dp.
 * Contains: sender→receiver row, divider, timestamp/badge/message/hashtags, divider, actions.
 */
@Composable
fun KudosCard(
    post: KudosPost,
    isLiked: Boolean,
    onLikeToggle: () -> Unit,
    onCopyLink: () -> Unit,
    onDetail: () -> Unit,
    modifier: Modifier = Modifier,
    onPersonClick: ((name: String) -> Unit)? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CardBg)
                .border(1.dp, CardBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Sender → Receiver row — node I6885:9092;89:2950
        KudosTransferRow(post = post, onPersonClick = onPersonClick)

        // Divider — Rectangle 14
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardDivider))

        // Content block — node I6885:9092;89:2956
        KudosContentBlock(post = post)

        // Divider — Rectangle 15
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardDivider))

        // Actions row — node I6885:9092;89:2972.
        KudosActionRow(
            heartCount = post.displayHeartCount(isLiked),
            isLiked = isLiked,
            onLikeToggle = onLikeToggle,
            onCopyLink = onCopyLink,
            onDetail = onDetail,
        )
    }
}

/**
 * Action row — hearts + Copy Link + Xem chi tiết.
 * Node I6885:9092;89:2972 (space-between, height 24dp).
 */
@Composable
fun KudosActionRow(
    heartCount: Int,
    isLiked: Boolean,
    onLikeToggle: () -> Unit,
    onCopyLink: () -> Unit,
    onDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Hearts — node I6885:9092;89:2973. Layout: [count] [heart] (Figma).
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onLikeToggle),
        ) {
            Text(
                text = heartCount.toString(),
                color = ActionText,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                painter =
                    painterResource(
                        if (isLiked) R.drawable.kudos_ic_heart_filled else R.drawable.kudos_ic_heart,
                    ),
                contentDescription = stringResource(R.string.kudos_like_content_desc),
                modifier = Modifier.size(16.dp),
                tint = if (isLiked) Color(0xFFFF4444) else Color(0xFF00101A),
            )
        }

        // Buttons row — node I6885:9092;89:2976. Each action: [text] [icon].
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Copy Link — text then chain-link icon
            Row(
                modifier = Modifier.clickable(onClick = onCopyLink),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.kudos_action_copy_link),
                    color = ActionText,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                )
                Icon(
                    painter = painterResource(R.drawable.kudos_ic_link),
                    contentDescription = stringResource(R.string.kudos_action_copy_link),
                    // Diagonal chain-link orientation per Figma.
                    modifier = Modifier.size(16.dp).rotate(-45f),
                    tint = ActionText,
                )
            }
            // Xem chi tiết — text then ↗ arrow (reuses home_kudos_ic_arrow)
            Row(
                modifier = Modifier.clickable(onClick = onDetail),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.kudos_action_view_detail),
                    color = ActionText,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                )
                Icon(
                    painter = painterResource(R.drawable.home_kudos_ic_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = ActionText,
                )
            }
        }
    }
}
