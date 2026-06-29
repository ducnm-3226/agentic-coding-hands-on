package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma mms_C_All kudos (6885:9220)
private val EyebrowColor = Color(0xFFFFFFFF)
private val SectionDivider = Color(0xFF2E3940)
private val TitleColor = Color(0xFFFFEA9E)

/**
 * ALL KUDOS section — Figma mms_C_All kudos (6885:9220).
 * Contains: section header, stats block, Top-10 recipients, feed list, "View all Kudos" link.
 */
@Composable
fun KudosAllSection(
    feedPosts: List<KudosPost>,
    stats: KudosStats,
    giftRecipients: List<GiftRecipient>,
    likedIds: Set<String>,
    onLikeToggle: (String) -> Unit,
    onCopyLink: (String) -> Unit,
    onDetail: (String) -> Unit,
    onOpenSecretBox: () -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier,
    onPersonClick: ((String) -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // Section header — node 6885:9221
        AllKudosSectionHeader()

        // Stats block D.1 — node 6885:9223
        KudosStatsBlock(
            stats = stats,
            onOpenSecretBox = onOpenSecretBox,
        )

        // Top-10 recipients — node mms_D.3 (6885:9255)
        TopRecipientsBlock(recipients = giftRecipients)

        // Feed list — Danh sách Kudo (6891:15986)
        if (feedPosts.isEmpty()) {
            KudosEmptyState()
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                feedPosts.forEach { post ->
                    KudosCard(
                        post = post,
                        isLiked = likedIds.contains(post.id),
                        onLikeToggle = { onLikeToggle(post.id) },
                        onCopyLink = { onCopyLink(post.id) },
                        onDetail = { onDetail(post.id) },
                        onPersonClick = onPersonClick,
                    )
                }
            }
        }

        // "View all Kudos" link — node 6891:15987
        ViewAllLink(onClick = onViewAll)
    }
}

@Composable
private fun AllKudosSectionHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(R.string.kudos_eyebrow_event),
            color = EyebrowColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SectionDivider),
        )
        Text(
            text = stringResource(R.string.kudos_section_all_title),
            color = TitleColor,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
    }
}
