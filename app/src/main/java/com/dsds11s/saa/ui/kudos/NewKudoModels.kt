package com.dsds11s.saa.ui.kudos

import android.net.Uri

/** Domain model for a recipient search result. */
data class KudoRecipient(
    val id: String,
    val name: String,
    val unit: String,
)

/** Complete form state held inside NewKudoScreen. Defaults match the empty "Viết Kudo_default" form. */
data class NewKudoFormState(
    val recipient: KudoRecipient? = null,
    val recipientQuery: String = "",
    val awardTitle: String = "",
    val message: String = "",
    val selectedHashtags: List<String> = emptyList(),
    val images: List<Uri> = emptyList(),
    val isAnonymous: Boolean = false,
    val showValidationError: Boolean = false,
    val showRecipientDropdown: Boolean = false,
    val showHashtagDropdown: Boolean = false,
)

/** Max input lengths — spec 7fFAb-K35a (B.4 title ≤100, D message ≤1000, F image ≤5). */
const val KUDO_TITLE_MAX = 100
const val KUDO_MESSAGE_MAX = 1000
const val KUDO_IMAGE_MAX = 5

/** Append [added] to [current], de-duplicating and keeping at most [max] items (spec F image cap). */
fun <T> appendCapped(
    current: List<T>,
    added: List<T>,
    max: Int,
): List<T> = (current + added).distinct().take(max)

// ── Message formatting transforms (toolbar tools C.4 numbered list / C.6 quote) ───────────────
// Whole-field transforms applied to the plain-text message; pure + unit-tested.

private val NUMBERED_PREFIX = Regex("^\\d+\\.\\s")
private val QUOTE_PREFIX = Regex("^>\\s")

/** Prefix each non-blank line with a running "n. " number. */
fun toNumberedList(text: String): String {
    if (text.isEmpty()) return text
    var n = 0
    return text.split("\n").joinToString("\n") { line ->
        if (line.isBlank()) line else "${++n}. ${line.replace(NUMBERED_PREFIX, "")}"
    }
}

/** Remove a leading "n. " from each line. */
fun stripNumberedList(text: String): String = text.split("\n").joinToString("\n") { it.replace(NUMBERED_PREFIX, "") }

/** Prefix each non-blank line with "> " (markdown quote). */
fun toQuotePrefix(text: String): String {
    if (text.isEmpty()) return text
    return text.split("\n").joinToString("\n") { line ->
        if (line.isBlank()) line else "> ${line.replace(QUOTE_PREFIX, "")}"
    }
}

/** Remove a leading "> " from each line. */
fun stripQuotePrefix(text: String): String = text.split("\n").joinToString("\n") { it.replace(QUOTE_PREFIX, "") }

/** All available hashtags from Figma screen aKWA2klsnt. */
val allHashtags: List<String> =
    listOf(
        "High-perorming",
        "BE PROFESSIONAL",
        "BE OPTIMISTIC",
        "BE A TEAM",
        "THINK OUTSIDE THE BOX",
        "GET RISKY",
        "GO FAST",
        "WASSHOI",
    )

/** Mock recipient search results from Figma screen 5MU728Tjck. */
val mockRecipients: List<KudoRecipient> =
    listOf(
        KudoRecipient("1", "Dương Huỳnh Xuân Nhật", "CECV1"),
        KudoRecipient("2", "Dương Huỳnh Xuân Nhân", "CECV1"),
    )

/**
 * Validates the New Kudo form submission — spec 7fFAb-K35a (send button rule I).
 *
 * Rules (all required):
 * - Recipient must not be null
 * - Title (Danh hiệu) must not be blank
 * - Message must not be blank
 * - At least 1 hashtag must be selected (max 5)
 *
 * @return true if form is valid and ready to submit, false otherwise
 */
fun isNewKudoFormValid(
    recipient: KudoRecipient?,
    title: String,
    message: String,
    selectedHashtags: List<String>,
): Boolean {
    return recipient != null &&
        title.isNotBlank() &&
        message.isNotBlank() &&
        selectedHashtags.isNotEmpty()
}
