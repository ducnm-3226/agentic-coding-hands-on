package com.dsds11s.saa.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.ui.theme.SAATheme

private val HomeBackground = Color(0xFF00101A)

/**
 * [iOS] Home screen (frame 6885:8978) assembled from its sections.
 * Scrollable content with the header overlaid on top and the nav bar + Kudos
 * FAB pinned to the bottom. The countdown ticks live via [rememberCountdown].
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAboutAwardClick: () -> Unit = {},
    onAboutKudosClick: () -> Unit = {},
    onKudosDetailClick: () -> Unit = {},
    onWriteKudosClick: () -> Unit = {},
    onOpenAward: (Int) -> Unit = {},
    onOpenKudos: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    hasNotificationBadge: Boolean = true,
) {
    val countdown = rememberCountdown()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize().background(HomeBackground)) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
        ) {
            HomeHero(
                days = countdown.days,
                hours = countdown.hours,
                minutes = countdown.minutes,
                seconds = countdown.seconds,
                isEnded = countdown.isEnded,
                onAboutMoreClick = onAboutAwardClick,
                onJoinClick = onAboutKudosClick,
            )
            Spacer(Modifier.height(40.dp))
            HomeNote(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(48.dp))
            HomeAwards(
                modifier = Modifier.padding(horizontal = 20.dp),
                onAwardClick = onOpenAward,
            )
            Spacer(Modifier.height(48.dp))
            HomeKudos(onDetailClick = onKudosDetailClick)
            // Clearance so the last content clears the pinned nav bar + FAB.
            Spacer(Modifier.height(120.dp))
        }

        HomeHeader(
            modifier = Modifier.align(Alignment.TopCenter),
            onNotificationClick = onNotificationClick,
            onSearchClick = onSearchClick,
            hasNotificationBadge = hasNotificationBadge,
        )

        HomeKudosFab(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(end = 20.dp, bottom = 88.dp),
            onClick = onWriteKudosClick,
        )

        HomeBottomNav(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedIndex = selectedTab,
            // Awards tab (1) → Awards detail; Kudos tab (2) → Kudos feed; others switch locally.
            onTabSelected = { tab ->
                when (tab) {
                    1 -> onOpenAward(0)
                    2 -> onOpenKudos()
                    else -> selectedTab = tab
                }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun HomeScreenPreview() {
    SAATheme {
        HomeScreen()
    }
}
