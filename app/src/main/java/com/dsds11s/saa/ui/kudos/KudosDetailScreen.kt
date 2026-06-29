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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.home.HomeBottomNav

private val ScreenBg = Color(0xFF00101A)

/**
 * KudosDetailScreen (View Kudo) — Figma [iOS] Sun*Kudos_View kudo (6885:10128).
 *
 * Shows the full detail of a single Kudo via [KudosDetailCard]. Presentational —
 * the post is resolved from the mock list by [postId]; like toggle is local state.
 *
 * @param postId         id of the Kudo to display (falls back to the first mock post)
 * @param onBack         back chevron tap (returns to the previous screen)
 * @param onTabSelected  bottom nav tab selection (index 0–3)
 */
@Composable
fun KudosDetailScreen(
    postId: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
) {
    // Resolve the post by id; fall back to the first available post for the demo.
    // firstOrNull (not first) so an empty feed renders nothing instead of crashing.
    val post =
        remember(postId) {
            kudosFeedPosts.firstOrNull { it.id == postId } ?: kudosFeedPosts.firstOrNull()
        } ?: return
    // Ephemeral by design (matches KudosScreen): the like toggle resets per visit since the
    // app is presentational with no backend to persist it. `remember(postId)` resets on nav.
    var isLiked by remember(postId) { mutableStateOf(post.liked) }

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

            KudosDetailCard(
                post = post,
                isLiked = isLiked,
                onLikeToggle = { isLiked = !isLiked },
                onCopyLink = { /* no-op: static */ },
                onDetail = { /* already on detail */ },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sticky top bar — floats over keyvisual.
        KudosTopBar(
            title = "Kudo",
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
