package com.dsds11s.saa.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dsds11s.saa.ui.theme.SAATheme

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun KudosScreenPreview() {
    SAATheme {
        KudosScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun KudosCardPreview() {
    SAATheme {
        KudosCard(
            post = kudosMockPosts.first(),
            isLiked = false,
            onLikeToggle = {},
            onCopyLink = {},
            onDetail = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun KudosStatsBlockPreview() {
    SAATheme {
        KudosStatsBlock(
            stats = kudosMockStats,
            onOpenSecretBox = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun KudosEmptyStatePreview() {
    SAATheme {
        KudosEmptyState()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun KudosSpotlightBoardPreview() {
    SAATheme {
        KudosSpotlightBoard(
            searchQuery = "",
            onSearchQueryChange = {},
        )
    }
}
