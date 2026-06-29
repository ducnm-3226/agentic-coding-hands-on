package com.dsds11s.saa.ui.locale

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Supported UI languages. [code] is the short label shown on the selector ("VN"/"EN") and is
 * NOT translated; [localeTag] is the Android resource qualifier driving which strings load.
 */
enum class AppLanguage(val code: String, val localeTag: String) {
    VIETNAMESE("VN", "vi"),
    ENGLISH("EN", "en"),
    ;

    companion object {
        /** Resolve from the short code shown on the selector; defaults to Vietnamese. */
        fun fromCode(code: String): AppLanguage = entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: VIETNAMESE
    }
}

/** Current app language, provided by [LocalizedApp]. Default Vietnamese (per spec). */
val LocalAppLanguage = staticCompositionLocalOf { AppLanguage.VIETNAMESE }

/** Setter to switch the app language at runtime; provided by [LocalizedApp]. */
val LocalSetAppLanguage = staticCompositionLocalOf<(AppLanguage) -> Unit> { {} }

/**
 * Persists the selected language across app restarts via SharedPreferences (no extra deps).
 */
object LocaleManager {
    private const val PREFS = "saa_prefs"
    private const val KEY_LANGUAGE = "app_language"

    fun load(context: Context): AppLanguage {
        val tag =
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_LANGUAGE, AppLanguage.VIETNAMESE.localeTag)
        return AppLanguage.entries.firstOrNull { it.localeTag == tag } ?: AppLanguage.VIETNAMESE
    }

    fun save(
        context: Context,
        language: AppLanguage,
    ) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, language.localeTag)
            .apply()
    }
}
