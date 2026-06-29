package com.dsds11s.saa.ui.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// ---------------------------------------------------------------------------
// Design tokens — Figma [iOS] Notifications (_b68CBWKl5)
// ---------------------------------------------------------------------------

internal val NotifListBg = Color(0x9900070C) // rgba(0,7,12,0.6) — list container bg
internal val NotifDivider = Color(0xFF2E3940) // Details-Divider #2E3940
internal val NotifTimestampColor = Color(0xFF999999) // muted grey #999999
internal val NotifUnreadDot = Color(0xFFD4271D) // red dot #D4271D
internal val NotifLinkColor = Color.White // community link text

// NotifMarkAllReadRow lives in NotifMarkAllReadRow.kt (same package).

// ---------------------------------------------------------------------------
// Single notification row (Figma mms_B.1_Noti / Noti variants)
// A.1.1 = icon, A.1.2 = content+timestamp, A.1.3 = unread dot
// ---------------------------------------------------------------------------

/**
 * One notification list item.
 *
 * @param item             the notification data
 * @param onClick          tap handler for the whole row
 * @param onCommunityLink  tap handler for the "Tiêu chuẩn cộng đồng" inline link
 *                         (only relevant when [item.communityLink] is non-null)
 * @param showDivider      whether to render the bottom divider (#2E3940)
 */
@Composable
fun NotificationRow(
    item: NotificationItem,
    onClick: () -> Unit,
    onCommunityLink: () -> Unit,
    showDivider: Boolean,
    modifier: Modifier = Modifier,
) {
    val spec = item.type.iconSpec()
    val iconTint = Color(spec.tintArgb)

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // mms_B.1.1_Icon — 24×24, top-aligned
            Icon(
                painter = painterResource(spec.drawableRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconTint,
            )

            Spacer(modifier = Modifier.width(16.dp))

            // mms_B.1.2_Content — fills remaining width
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Main content text — bold for unread (first item in design is bold/unread)
                Text(
                    text = item.content,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = if (!item.isRead) FontWeight.Bold else FontWeight.Normal,
                    letterSpacing = 0.25.sp,
                )

                // Inline community-link row (CONTENT_HIDDEN type only — Figma 6885:8836)
                if (item.communityLink != null) {
                    CommunityLinkRow(
                        label = item.communityLink,
                        onClick = onCommunityLink,
                    )
                }

                // Relative timestamp — muted grey, 12sp
                Text(
                    text = item.relativeTime,
                    color = NotifTimestampColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // mms_B.1.3_Group 425 — 8×8 red dot, only when unread
            Box(modifier = Modifier.size(16.dp), contentAlignment = Alignment.Center) {
                if (!item.isRead) {
                    Box(
                        modifier =
                            Modifier
                                .size(8.dp)
                                .background(NotifUnreadDot, CircleShape),
                    )
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(
                color = NotifDivider,
                thickness = 1.dp,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Inline community-standards link (Figma "Button tiêu chuẩn cộng đồng" 128:3467)
// ---------------------------------------------------------------------------

@Composable
private fun CommunityLinkRow(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text =
                buildAnnotatedString {
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(label)
                    }
                },
            color = NotifLinkColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        Icon(
            painter = painterResource(R.drawable.kudos_ic_arrow_right),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.White,
        )
    }
}

// NotificationList lives in NotificationList.kt (same package).
