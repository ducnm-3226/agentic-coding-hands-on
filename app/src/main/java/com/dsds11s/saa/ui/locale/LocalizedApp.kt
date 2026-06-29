package com.dsds11s.saa.ui.locale

import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

/**
 * Wraps the app content with a locale-overridden [android.content.Context] + [Configuration] so
 * that `stringResource()` resolves the chosen [AppLanguage] and recomposes INSTANTLY when the
 * language changes — no activity recreate, no reload. The selection is persisted via [LocaleManager].
 *
 * Place inside the theme, around the whole navigation graph, so every screen inherits the override.
 */
@Composable
fun LocalizedApp(content: @Composable () -> Unit) {
    val context = LocalContext.current
    var language by remember { mutableStateOf(LocaleManager.load(context)) }

    // Rebuild the localized context + configuration whenever the language changes.
    // Use ContextThemeWrapper (NOT createConfigurationContext): it keeps the original Activity
    // in the base-context chain, so owner lookups that walk LocalContext — e.g.
    // rememberLauncherForActivityResult / ViewModel store owner — still resolve the Activity.
    // createConfigurationContext returns a context detached from the Activity, which crashes
    // any screen that registers an ActivityResult launcher (e.g. New Kudo's image picker).
    val localizedContext =
        remember(language) {
            val config =
                Configuration(context.resources.configuration).apply {
                    setLocale(Locale.forLanguageTag(language.localeTag))
                }
            ContextThemeWrapper(context, 0).apply { applyOverrideConfiguration(config) }
        }
    val localizedConfig =
        remember(language) {
            Configuration(localizedContext.resources.configuration)
        }

    CompositionLocalProvider(
        LocalAppLanguage provides language,
        LocalSetAppLanguage provides { selected ->
            language = selected
            LocaleManager.save(context, selected)
        },
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedConfig,
    ) {
        content()
    }
}
