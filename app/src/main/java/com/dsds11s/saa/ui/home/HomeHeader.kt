package com.dsds11s.saa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.locale.LanguageSelector
import com.dsds11s.saa.ui.theme.SAATheme

// Screen-scoped colors — Figma node 6885:9057
// Header bg: vertical gradient #00101A 0% → #00101A30 76% → transparent 100%, opacity 0.9
private val HomeHeaderBgStart = Color(0xFF00101A)
private val HomeHeaderBgMid = Color(0x4D00101A) // ~30% opacity at 76% stop (Figma 76% stop)
private val HomeHeaderBadgeDot = Color(0xFFD4271D) // node I6885:9057;88:1830;72:1628 #D4271D

/**
 * Home screen header bar (node mms_1_header 6885:9057).
 * Contains: Sun* Award logo (left), language selector + search + notification (right).
 * Rendered over the keyvisual; top padding handled via statusBarsPadding().
 * The iOS StatusBar node is SKIPPED — Android system status bar owns that space.
 *
 * @param onLanguageClick  callback for language selector tap
 * @param onSearchClick    callback for search icon tap
 * @param onNotificationClick  callback for notification icon tap
 * @param hasNotificationBadge  whether the bell shows the unread red dot (driven by unread count)
 */
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onLanguageClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    hasNotificationBadge: Boolean = true,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        0f to HomeHeaderBgStart.copy(alpha = 0.9f),
                        0.7644f to HomeHeaderBgMid.copy(alpha = 0.9f),
                        1f to Color.Transparent,
                    ),
                )
                .statusBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Logo — reuse login asset, same 48x44dp sizing per Figma node I6885:9057;88:1827
            Image(
                painter = painterResource(R.drawable.login_logo_homepage),
                contentDescription = stringResource(R.string.home_header_logo_desc),
                modifier = Modifier.size(width = 48.dp, height = 44.dp),
            )

            // Actions frame: language + search + notification (122x32dp, gap 10dp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LanguageSelector()
                HomeIconButton(
                    iconRes = R.drawable.home_hdr_ic_search,
                    contentDescription = stringResource(R.string.home_header_search_desc),
                    onClick = onSearchClick,
                )
                HomeNotificationButton(
                    onClick = onNotificationClick,
                    hasBadge = hasNotificationBadge,
                )
            }
        }
    }
}

/** Simple 24x24 tappable icon button */
@Composable
private fun HomeIconButton(
    iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(24.dp)
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
        )
    }
}

/**
 * Notification icon with optional red badge dot (8x8dp, #D4271D).
 * Figma nodes I6885:9057;88:1830 (bell 24dp) + I6885:9057;88:1830;72:1628 (dot top-right).
 */
@Composable
private fun HomeNotificationButton(
    onClick: () -> Unit,
    hasBadge: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(24.dp)
                .clickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(R.drawable.home_hdr_ic_notification),
            contentDescription = stringResource(R.string.home_header_notifications_desc),
            modifier = Modifier.size(24.dp),
        )
        if (hasBadge) {
            Box(
                modifier =
                    Modifier
                        .size(8.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(100.dp))
                        .background(HomeHeaderBadgeDot),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun HomeHeaderPreview() {
    SAATheme {
        HomeHeader()
    }
}
