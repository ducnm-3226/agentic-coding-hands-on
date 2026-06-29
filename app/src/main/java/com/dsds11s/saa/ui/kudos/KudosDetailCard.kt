package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma [iOS] Sun*Kudos_View kudo (6885:10128)
private val CardBg = Color(0xFFFFF8E1)
private val CardBorder = Color(0xFFFFEA9E)
private val CardDivider = Color(0xFFFFEA9E)
private val TimestampColor = Color(0xFF999999)
private val TextDark = Color(0xFF00101A)
private val HashtagRed = Color(0xFFE73928)

/**
 * Detail (View Kudo) card — Figma [iOS] Sun*Kudos_View kudo (6885:10128).
 *
 * Visually the focal card: full (untruncated) message, distinct bold Kudo title,
 * sender→receiver transfer row with avatars + department, attached-image gallery,
 * hashtags, and the shared [KudosActionRow].
 */
@Composable
fun KudosDetailCard(
    post: KudosPost,
    isLiked: Boolean,
    onLikeToggle: () -> Unit,
    onCopyLink: () -> Unit,
    onDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CardBg)
                .border(1.dp, CardBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Sender → Receiver transfer row (B.3).
        DetailTransferRow(post = post)

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardDivider))

        // Timestamp (B.4.1).
        Text(
            text = post.timestamp,
            color = TimestampColor,
            fontSize = 10.sp,
            lineHeight = 11.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.23.sp,
        )

        // Kudo title (B.4.0) — distinct from badge label, bold + centered.
        Text(
            text = post.title ?: post.badgeLabel,
            color = TextDark,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        // Full message body (B.4.2) — no truncation on the detail screen.
        Text(
            text = post.message,
            color = TextDark,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth(),
        )

        // Attached-image gallery (F.2) — placeholder thumbnails, max 5.
        if (post.galleryImageCount > 0) {
            DetailImageGallery(count = post.galleryImageCount)
        }

        // Hashtags (B.4.3).
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

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardDivider))

        // Action row (B.4.4) — reused from the list cards.
        KudosActionRow(
            heartCount = post.displayHeartCount(isLiked),
            isLiked = isLiked,
            onLikeToggle = onLikeToggle,
            onCopyLink = onCopyLink,
            onDetail = onDetail,
        )
    }
}
