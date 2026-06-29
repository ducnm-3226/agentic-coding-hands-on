package com.dsds11s.saa.ui.login

/**
 * Supported languages for the Login screen copy.
 * Test contract: Language.entries must enumerate all variants; each must have distinct
 * non-blank description and copyright (see LoginStringsTest).
 */
enum class Language {
    VIETNAMESE,
    ENGLISH,
}

/** Localized string bundle for the Login screen. */
data class LoginStrings(
    val description: String,
    val copyright: String,
)

/**
 * Returns the [LoginStrings] for the given [language].
 * English copy matches the design spec asserted by LoginStringsTest.englishCopy_matchesDesign.
 */
fun loginStringsFor(language: Language): LoginStrings =
    when (language) {
        Language.ENGLISH ->
            LoginStrings(
                description = "Start your journey with SAA 2025.\nLog in to explore!",
                copyright = "Copyright belongs to Sun* © 2025",
            )
        Language.VIETNAMESE ->
            LoginStrings(
                description = "Bắt đầu hành trình của bạn với SAA 2025.\nĐăng nhập để khám phá!",
                copyright = "Bản quyền thuộc về Sun* © 2025",
            )
    }
