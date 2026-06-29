package com.dsds11s.saa.ui.notifications

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.kudos.KudosTopBar

// Screen-level design token — matches other screens' dark bg (#00101A)
private val NotifScreenBg = Color(0xFF00101A)

/**
 * NotificationsScreen — Figma [iOS] Notifications (_b68CBWKl5).
 *
 * Presentational composable: no ViewModel, no NavController.
 * All side-effects are surfaced as callbacks.
 *
 * Integration contract:
 * @param notifications         list of [NotificationItem] — supply from ViewModel / repository
 * @param onBack                back chevron tap → pop back stack
 * @param onMarkAllRead         "Đánh dấu đọc tất cả" row tap
 * @param onNotificationClick   tap on any notification row (receives the tapped item)
 * @param onCommunityLinkClick  tap on the inline "Tiêu chuẩn cộng đồng" link
 *                              (only fires for CONTENT_HIDDEN items)
 */
@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    onBack: () -> Unit,
    onMarkAllRead: () -> Unit,
    onNotificationClick: (NotificationItem) -> Unit,
    onCommunityLinkClick: (NotificationItem) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(NotifScreenBg),
    ) {
        // Aurora/keyvisual hero background — same pattern as KudosScreen / CommunityStandardsScreen
        HeroKeyvisualBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(520.dp)
                    .align(Alignment.TopStart),
        )

        // Scrollable content column
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 32.dp),
        ) {
            // Space for the overlaid sticky top bar (56dp = statusBarsPadding + 42dp nav row)
            Spacer(modifier = Modifier.height(101.dp))

            // A.1 — "Đánh dấu đọc tất cả" action row (Figma 6885:9392, y=101)
            NotifMarkAllReadRow(
                onClick = onMarkAllRead,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // B — Notification list container (Figma 6885:9393, y=153)
            NotificationList(
                notifications = notifications,
                onItemClick = onNotificationClick,
                onCommunityLink = onCommunityLinkClick,
            )
        }

        // Sticky top bar — overlaid on top of scroll content (same pattern as KudosTopBar)
        KudosTopBar(
            title = stringResource(R.string.notif_title),
            onBack = onBack,
            modifier = Modifier.align(Alignment.TopStart),
        )
    }
}
