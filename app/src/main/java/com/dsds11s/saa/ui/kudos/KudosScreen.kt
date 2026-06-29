package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.awards.AwardKvKudosHeader
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.home.HomeBottomNav
import com.dsds11s.saa.ui.home.HomeHeader

// Screen-scoped design tokens — Figma [iOS] Sun*Kudos (6885:9059)
private val ScreenBg = Color(0xFF00101A) // dark bg #00101A

/**
 * KudosScreen — Figma [iOS] Sun*Kudos (6885:9059).
 *
 * Presentational composable with full local interaction state.
 * Caller must supply navigation callbacks; no ViewModel dependency.
 *
 * @param onLanguageClick       header language selector tap
 * @param onSearchClick         header search icon tap
 * @param onNotificationClick   header notification icon tap
 * @param onSendKudos           send-kudos bar tap
 * @param onKudosDetail         tap "Xem chi tiết" on any card (receives post id)
 * @param onOpenSecretBox       "Mở Secret Box" button tap
 * @param onViewAllKudos        "View all Kudos" link tap
 * @param onTabSelected         bottom nav tab selection (index 0–3)
 */
@Composable
fun KudosScreen(
    modifier: Modifier = Modifier,
    onLanguageClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSendKudos: () -> Unit = {},
    onKudosDetail: (String) -> Unit = {},
    onOpenSecretBox: () -> Unit = {},
    onViewAllKudos: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onPersonClick: (String) -> Unit = {},
    hasNotificationBadge: Boolean = true,
) {
    // ── Local interaction state ──────────────────────────────────────────────
    // Liked post ids (seeded from the mock baseline). Card display counts derive
    // from this set vs each post's baseline `liked` — no separate count map needed.
    var likedIds by remember {
        mutableStateOf(
            kudosMockPosts.filter { it.liked }.map { it.id }.toSet(),
        )
    }
    val toggleLike: (String) -> Unit = { id ->
        likedIds = if (id in likedIds) likedIds - id else likedIds + id
    }
    // Filter selections + spotlight search (survive config changes)
    var selectedHashtag by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedDepartment by rememberSaveable { mutableStateOf<String?>(null) }
    var spotlightSearch by rememberSaveable { mutableStateOf("") }

    // ── Filtered post lists (AND logic) ──────────────────────────────────────
    val filteredHighlight =
        remember(selectedHashtag, selectedDepartment) {
            kudosHighlightPosts.filter { post ->
                (selectedHashtag == null || post.hashtags.contains(selectedHashtag)) &&
                    (selectedDepartment == null || post.department == selectedDepartment)
            }
        }
    val filteredFeed =
        remember(selectedHashtag, selectedDepartment) {
            kudosFeedPosts.filter { post ->
                (selectedHashtag == null || post.hashtags.contains(selectedHashtag)) &&
                    (selectedDepartment == null || post.department == selectedDepartment)
            }
        }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(ScreenBg),
    ) {
        // Shared keyvisual + gradient background — identical to the Awards screen.
        HeroKeyvisualBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(520.dp)
                    .align(Alignment.TopStart),
        )

        // ── Scrollable content ───────────────────────────────────────────────
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
            // space for sticky bottom nav
        ) {
            // 1. Clear the sticky header, then the KUDOS hero — same layout as Awards.
            Spacer(modifier = Modifier.height(100.dp))
            AwardKvKudosHeader()

            // 3. Send-Kudos bar — node mms_A.1_Button ghi nhận (6885:9083)
            KudosSendBar(
                text = stringResource(R.string.kudos_send_bar_prompt),
                onClick = onSendKudos,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            )

            // 4. HIGHLIGHT KUDOS section — node mms_B_Highlight (6885:9084)
            KudosHighlightSection(
                posts = filteredHighlight,
                likedIds = likedIds,
                selectedHashtag = selectedHashtag,
                selectedDepartment = selectedDepartment,
                hashtagOptions = kudosHashtagOptions,
                departmentOptions = kudosDepartmentOptions,
                onHashtagSelected = { selectedHashtag = it },
                onDepartmentSelected = { selectedDepartment = it },
                onLikeToggle = toggleLike,
                onCopyLink = { /* no-op: static */ },
                onDetail = onKudosDetail,
                onPersonClick = onPersonClick,
                // No horizontal padding — the section insets its own header/dots so the
                // carousel can extend near edge-to-edge (Figma mms_B.2).
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 5. SPOTLIGHT BOARD — node B.6 Spotlight board (6885:9099)
            KudosSpotlightBoard(
                searchQuery = spotlightSearch,
                onSearchQueryChange = { spotlightSearch = it },
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 6. ALL KUDOS section — node mms_C_All kudos (6885:9220)
            KudosAllSection(
                feedPosts = filteredFeed,
                stats = kudosMockStats,
                giftRecipients = kudosGiftRecipients,
                likedIds = likedIds,
                onLikeToggle = toggleLike,
                onCopyLink = { /* no-op: static */ },
                onDetail = onKudosDetail,
                onOpenSecretBox = onOpenSecretBox,
                onViewAll = onViewAllKudos,
                onPersonClick = onPersonClick,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // 1. Sticky HomeHeader — floats over keyvisual
        HomeHeader(
            modifier = Modifier.align(Alignment.TopStart),
            onLanguageClick = onLanguageClick,
            onSearchClick = onSearchClick,
            onNotificationClick = onNotificationClick,
            hasNotificationBadge = hasNotificationBadge,
        )

        // 7. Sticky bottom nav — Kudos tab (index 2) active
        HomeBottomNav(
            selectedIndex = 2,
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomStart),
        )
    }
}
