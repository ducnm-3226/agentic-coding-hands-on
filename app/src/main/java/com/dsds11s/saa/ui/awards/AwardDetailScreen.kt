package com.dsds11s.saa.ui.awards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.home.HomeBottomNav
import com.dsds11s.saa.ui.home.HomeHeader
import com.dsds11s.saa.ui.home.HomeKudos
import com.dsds11s.saa.ui.theme.SAATheme

private val ScreenBg = Color(0xFF00101A)

/**
 * Award detail screen — Figma [iOS] Award_* frames (one template, 6 award variants).
 *
 * Sections (top → bottom, matching Figma overview):
 *   A. HomeHeader (sticky overlay — logo + lang + search + bell)
 *   KV. AwardKvKudosHeader — "Hệ thống ghi nhận và cảm ơn" + flame + KUDOS wordmark
 *   B. AwardHighlightBlock — eyebrow + title + dropdown selector
 *   C. AwardDetailContent — trophy placeholder + title + description + quantity + prizes
 *   D. HomeKudos — Sun* Kudos section (reused from Home)
 *   Nav. HomeBottomNav — Awards tab (index 1) active
 *
 * Stateless: all state is lifted to the caller (MainActivity). No ViewModel.
 *
 * @param award               The currently displayed award (drives all content)
 * @param awards              Full list used to populate the dropdown
 * @param onAwardSelected     Called with award.id when user picks from dropdown
 * @param onTabSelected       Bottom nav tab tap (0=Home,1=Awards,2=Kudos,3=Profile)
 * @param onKudosDetailClick  "Chi tiết" button in the Kudos section
 * @param onNotificationClick Header bell icon tap
 * @param onSearchClick       Header search icon tap
 * @param hasNotificationBadge Whether the bell shows the red unread dot
 */
@Composable
fun AwardDetailScreen(
    award: Award,
    awards: List<Award>,
    onAwardSelected: (String) -> Unit,
    onTabSelected: (Int) -> Unit,
    onKudosDetailClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSearchClick: () -> Unit,
    hasNotificationBadge: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(ScreenBg),
    ) {
        // Scrollable content (root Box already paints ScreenBg, so the keyvisual band
        // below shows through the top — the lower sections sit on that navy base).
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
        ) {
            // Top keyvisual band — single shared artwork behind the header overlay,
            // the KV-Kudos banner AND the highlight block (matches the Home hero treatment;
            // fixes the previously-too-short 200dp background).
            Box(modifier = Modifier.fillMaxWidth()) {
                HeroKeyvisualBackground(modifier = Modifier.matchParentSize())
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Clearance for the sticky header overlay (~100dp matches KudosScreen)
                    Spacer(modifier = Modifier.height(100.dp))

                    // A — KV Kudos banner (shared with KudosScreen)
                    AwardKvKudosHeader()

                    Spacer(modifier = Modifier.height(24.dp))

                    // B — Highlight block (eyebrow + title + full-width dropdown selector)
                    AwardHighlightBlock(
                        currentAward = award,
                        awards = awards,
                        onAwardSelected = onAwardSelected,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            // C — Detail content: trophy + title + description + quantity + prizes
            AwardDetailContent(
                award = award,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // D — Sun* Kudos section (HomeKudos reused)
            HomeKudos(
                onDetailClick = onKudosDetailClick,
            )

            // Bottom clearance for sticky nav bar
            Spacer(modifier = Modifier.height(88.dp))
        }

        // Sticky HomeHeader — overlaid on top of keyvisual
        HomeHeader(
            modifier = Modifier.align(Alignment.TopStart),
            onNotificationClick = onNotificationClick,
            onSearchClick = onSearchClick,
            hasNotificationBadge = hasNotificationBadge,
        )

        // Sticky bottom nav — Awards tab (index 1) active
        HomeBottomNav(
            selectedIndex = 1,
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomStart),
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun AwardDetailScreenPreview() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards.first(),
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = true,
        )
    }
}
