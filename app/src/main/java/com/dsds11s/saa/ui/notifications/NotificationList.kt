package com.dsds11s.saa.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Scrollable list container with semi-transparent dark background and rounded corners.
 * Renders each [NotificationRow] separated by a #2E3940 divider.
 *
 * @param notifications    items to display
 * @param onItemClick      row tap callback
 * @param onCommunityLink  community-link tap (item passed for context)
 */
@Composable
fun NotificationList(
    notifications: List<NotificationItem>,
    onItemClick: (NotificationItem) -> Unit,
    onCommunityLink: (NotificationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = NotifListBg,
                    shape = RoundedCornerShape(8.dp),
                ),
    ) {
        notifications.forEachIndexed { index, item ->
            NotificationRow(
                item = item,
                onClick = { onItemClick(item) },
                onCommunityLink = { onCommunityLink(item) },
                showDivider = index < notifications.lastIndex,
            )
        }
    }
}
