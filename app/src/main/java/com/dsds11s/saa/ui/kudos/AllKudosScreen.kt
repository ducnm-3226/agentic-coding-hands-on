package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.home.HomeBottomNav
import com.dsds11s.saa.ui.theme.Montserrat

// Screen-scoped design tokens — Figma [iOS] Sun*Kudos_All Kudos (6891:15995)
private val ScreenBg = Color(0xFF00101A)
private val EyebrowColor = Color(0xFFFFFFFF)
private val SectionDivider = Color(0xFF2E3940)
private val TitleColor = Color(0xFFFFEA9E)

/**
 * AllKudosScreen — Figma [iOS] Sun*Kudos_All Kudos (6891:15995).
 *
 * Full-screen vertical list of every Kudos card. Reuses [KudosCard].
 * Presentational — caller supplies navigation callbacks; like toggle is local state.
 *
 * @param onBack          back chevron tap (returns to Kudos home)
 * @param onKudosDetail   "Xem chi tiết" on any card (receives post id)
 * @param onTabSelected   bottom nav tab selection (index 0–3)
 */
@Composable
fun AllKudosScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onKudosDetail: (String) -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onPersonClick: (String) -> Unit = {},
) {
    // Liked post ids seeded from the mock baseline (same model as KudosScreen).
    var likedIds by remember {
        mutableStateOf(kudosFeedPosts.filter { it.liked }.map { it.id }.toSet())
    }
    val toggleLike: (String) -> Unit = { id ->
        likedIds = if (id in likedIds) likedIds - id else likedIds + id
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(ScreenBg),
    ) {
        HeroKeyvisualBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .align(Alignment.TopStart),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
            // space for sticky bottom nav
        ) {
            // Clear the sticky top bar.
            Spacer(modifier = Modifier.height(56.dp))

            // Section header — "Sun* Annual Awards 2025" eyebrow + "ALL KUDOS" title.
            AllKudosHeader(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp))

            // Feed list — every card (Danh sách Kudo 6891:15986).
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                kudosFeedPosts.forEach { post ->
                    KudosCard(
                        post = post,
                        isLiked = likedIds.contains(post.id),
                        onLikeToggle = { toggleLike(post.id) },
                        onCopyLink = { /* no-op: static */ },
                        onDetail = { onKudosDetail(post.id) },
                        onPersonClick = onPersonClick,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sticky top bar — floats over keyvisual.
        KudosTopBar(
            title = stringResource(R.string.kudos_all_screen_title),
            onBack = onBack,
            modifier = Modifier.align(Alignment.TopStart),
        )

        // Sticky bottom nav — Kudos tab (index 2) active.
        HomeBottomNav(
            selectedIndex = 2,
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomStart),
        )
    }
}

@Composable
private fun AllKudosHeader(modifier: Modifier = Modifier) {
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
