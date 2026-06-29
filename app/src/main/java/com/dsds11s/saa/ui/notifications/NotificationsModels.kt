package com.dsds11s.saa.ui.notifications

import androidx.annotation.DrawableRes
import com.dsds11s.saa.R

// ---------------------------------------------------------------------------
// Notification type enum — 7 types from Figma [iOS] Notifications (_b68CBWKl5)
// ---------------------------------------------------------------------------

enum class NotificationType {
    /** mms_B.1_Noti (componentId 6885:8820) — envelope icon, blue tint */
    KUDOS_RECEIVED,

    /** Noti variant — heart icon, pink tint */
    HEART_RECEIVED,

    /** Noti variant — gift icon, green tint */
    SECRET_BOX_UNLOCK,

    /** Noti variant — star icon, yellow tint */
    LEVEL_UP,

    /** Noti variant (componentId 6885:8836, has inline link row) — warning icon, yellow tint */
    CONTENT_HIDDEN,

    /** Noti variant — badge/shield icon, green tint */
    BADGE_COLLECTED,

    /** Noti variant — pen/review icon, purple tint */
    REVIEW_REQUEST,
}

// ---------------------------------------------------------------------------
// Icon spec per type — drawable resource + tint color (ARGB)
// ---------------------------------------------------------------------------

data class NotifIconSpec(
    @param:DrawableRes val drawableRes: Int,
    val tintArgb: Long,
)

fun NotificationType.iconSpec(): NotifIconSpec =
    when (this) {
        NotificationType.KUDOS_RECEIVED ->
            NotifIconSpec(R.drawable.notif_ic_mail, 0xFF5B9FEF)
        NotificationType.HEART_RECEIVED ->
            NotifIconSpec(R.drawable.kudos_ic_heart, 0xFFE96A7C)
        NotificationType.SECRET_BOX_UNLOCK ->
            NotifIconSpec(R.drawable.kudos_ic_gift, 0xFF4CAF78)
        NotificationType.LEVEL_UP ->
            NotifIconSpec(R.drawable.notif_ic_star, 0xFFFFD233)
        NotificationType.CONTENT_HIDDEN ->
            NotifIconSpec(R.drawable.notif_ic_warning, 0xFFFFD233)
        NotificationType.BADGE_COLLECTED ->
            NotifIconSpec(R.drawable.notif_ic_badge, 0xFF4CAF78)
        NotificationType.REVIEW_REQUEST ->
            NotifIconSpec(R.drawable.kudos_ic_pen, 0xFFBB6BE8)
    }

// ---------------------------------------------------------------------------
// NotificationItem — the domain model the backend/integration layer must satisfy
// ---------------------------------------------------------------------------

/**
 * A single notification entry.
 *
 * Integration contract:
 * - [id]               — opaque string identifier from backend
 * - [type]             — drives icon + tint rendering; must be one of [NotificationType]
 * - [content]          — pre-formatted display text (backend resolves template variables)
 * - [relativeTime]     — human-readable relative timestamp, e.g. "15 phút trước"
 * - [isRead]           — false → red dot indicator shown (Figma mms_B.1.3)
 * - [communityLink]    — non-null only for [NotificationType.CONTENT_HIDDEN]; shown as tappable link
 * - [postId]           — referenced Kudo post id for types that open Kudo detail
 *                        (KUDOS_RECEIVED / HEART_RECEIVED); null for other types
 */
data class NotificationItem(
    val id: String,
    val type: NotificationType,
    val content: String,
    val relativeTime: String,
    val isRead: Boolean = false,
    val communityLink: String? = null,
    val postId: String? = null,
)
