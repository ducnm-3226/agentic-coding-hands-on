package com.dsds11s.saa.ui.awards

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dsds11s.saa.ui.theme.SAATheme

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – Signature")
@Composable
private fun PreviewSignature() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[0],
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

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – MVP")
@Composable
private fun PreviewMvp() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[1],
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = false,
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – Top Talent")
@Composable
private fun PreviewTopTalent() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[2],
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = false,
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – Top Project")
@Composable
private fun PreviewTopProject() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[3],
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = false,
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – Top Project Leader")
@Composable
private fun PreviewTopProjectLeader() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[4],
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = false,
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812, name = "Award Detail – Best Manager")
@Composable
private fun PreviewBestManager() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardDetailScreen(
            award = awards[5],
            awards = awards,
            onAwardSelected = {},
            onTabSelected = {},
            onKudosDetailClick = {},
            onNotificationClick = {},
            onSearchClick = {},
            hasNotificationBadge = false,
        )
    }
}
