package com.dsds11s.saa.ui.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dsds11s.saa.ui.theme.SAATheme

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun NotificationsScreenPreview() {
    SAATheme {
        NotificationsScreen(
            notifications = notificationsMockList,
            onBack = {},
            onMarkAllRead = {},
            onNotificationClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun NotificationRowUnreadPreview() {
    SAATheme {
        NotificationRow(
            item = notificationsMockList.first(),
            onClick = {},
            onCommunityLink = {},
            showDivider = true,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun NotificationRowContentHiddenPreview() {
    SAATheme {
        NotificationRow(
            item = notificationsMockList.first { it.type == NotificationType.CONTENT_HIDDEN },
            onClick = {},
            onCommunityLink = {},
            showDivider = false,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun NotifMarkAllReadRowPreview() {
    SAATheme {
        NotifMarkAllReadRow(onClick = {})
    }
}
