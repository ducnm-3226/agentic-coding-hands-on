package com.dsds11s.saa.ui.kudos

import androidx.annotation.DrawableRes

// Data models for KudosScreen — Figma mms_B.3_KUDO (6885:8424), mms_D.1 (6885:9223)

/** A single Kudos post (used in both Highlight carousel and All Kudos feed). */
data class KudosPost(
    val id: String,
    val senderName: String,
    val receiverName: String,
    val department: String,
    // 1–5, maps to hoa-thị star count
    val badgeLevel: Int,
    val message: String,
    val hashtags: List<String>,
    val heartCount: Int,
    val liked: Boolean = false,
    @DrawableRes val senderAvatarRes: Int? = null,
    @DrawableRes val imageRes: Int? = null,
    val timestamp: String = "10:00 - 10/30/2025",
    val badgeLabel: String = "IDOL GIỚI TRẺ",
    /** Distinct Kudo title shown on the detail screen (Figma B.4.0). Null falls back to badgeLabel. */
    val title: String? = null,
    /** Number of attached image thumbnails (max 5) — rendered as placeholders on the detail screen. */
    val galleryImageCount: Int = 0,
)

/**
 * Heart count to display = the mock baseline adjusted by the user's local like toggle.
 * Shared by every card variant (highlight, feed, detail) so the arithmetic stays in one place.
 */
fun KudosPost.displayHeartCount(isLiked: Boolean): Int = heartCount + ((if (isLiked) 1 else 0) - (if (liked) 1 else 0))

/** Top-10 sunner gift recipient row — Figma mms_D.3.2 (6885:9259). */
data class GiftRecipient(
    val name: String,
    val department: String,
    val giftDescription: String = "Nhận được 1 áo phông SAA",
    @DrawableRes val avatarRes: Int? = null,
)

/** Personal stats block — Figma mms_D.1 (6885:9223). */
data class KudosStats(
    val heartsReceived: Int,
    val heartsGiven: Int,
    val kudosReceived: Int,
    val kudosGiven: Int,
    val secretBoxOpened: Int,
    val secretBoxUnopened: Int,
)
