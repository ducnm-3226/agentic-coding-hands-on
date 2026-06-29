package com.dsds11s.saa.ui.locale

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Figma node 6891:15595 — mms_A_Dropdown-List
// Panel: bg #00070C, border 1dp #998C5F, border-radius 8dp, padding 6dp
private val DropdownBg = Color(0xFF00070C)
private val DropdownBorder = Color(0xFF998C5F)

// Selected row: rgba(255,234,158,0.20) — gold tint at 20% opacity
private val SelectedRowBg = Color(0xFFFFEA9E).copy(alpha = 0.20f)

// Text: white, Montserrat Medium 14sp/20sp (Figma node Awards Information Navigation Links)
private val DropdownLabelColor = Color.White

/** Language option entry shown in the dropdown panel. */
private data class LanguageOption(
    val code: String,
    val flagRes: Int,
)

private val languageOptions =
    listOf(
        LanguageOption("VN", R.drawable.login_ic_vn_flag),
        LanguageOption("EN", R.drawable.locale_ic_en_flag),
    )

/**
 * Language selection dropdown panel anchored via [Popup] to the caller's layout anchor.
 *
 * Figma design: "[iOS] Language dropdown" — node mms_A_Dropdown-List (6891:15595).
 * The panel is purely presentational; no locale/persistence logic lives here.
 *
 * @param expanded   whether the dropdown is visible
 * @param selected   currently active language code — "VN" or "EN"
 * @param onSelect   invoked with the chosen language code when user taps a row
 * @param onDismiss  invoked when the popup should close (tap outside or selection)
 * @param modifier   applied to the outer [Popup] content column
 */
@Composable
fun LanguageDropdownMenu(
    expanded: Boolean,
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!expanded) return

    Popup(
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = true),
    ) {
        DropdownPanel(
            selected = selected,
            onSelect = { code ->
                onSelect(code)
                onDismiss()
            },
            modifier = modifier,
        )
    }
}

/**
 * The visual dropdown panel — dark rounded card with VN / EN rows.
 * Extracted as a standalone composable so it can be previewed in isolation.
 */
@Composable
internal fun DropdownPanel(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier =
            modifier
                .clip(shape)
                .background(DropdownBg)
                .border(width = 1.dp, color = DropdownBorder, shape = shape)
                .padding(6.dp),
    ) {
        languageOptions.forEach { option ->
            LanguageRow(
                option = option,
                isSelected = option.code == selected,
                onSelect = onSelect,
            )
        }
    }
}

/**
 * Single row in the dropdown: [flag 24dp] [code label].
 * Selected state: gold-tinted bg, border-radius 2dp (Figma node mms_A.1_tiếng Việt).
 * Unselected: transparent bg (Figma node mms_A.2_tiếng Anh).
 */
@Composable
private fun LanguageRow(
    option: LanguageOption,
    isSelected: Boolean,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clip(RoundedCornerShape(if (isSelected) 2.dp else 0.dp))
                .background(if (isSelected) SelectedRowBg else Color.Transparent)
                .clickable { onSelect(option.code) }
                .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(option.flagRes),
            contentDescription = option.code,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = option.code,
            color = DropdownLabelColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.1.sp,
        )
    }
}

// ---------------------------------------------------------------------------
// Previews
// ---------------------------------------------------------------------------

@Preview(showBackground = true, backgroundColor = 0xFF00101A, name = "VN Selected")
@Composable
private fun PreviewVnSelected() {
    SAATheme {
        DropdownPanel(
            selected = "VN",
            onSelect = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, name = "EN Selected")
@Composable
private fun PreviewEnSelected() {
    SAATheme {
        DropdownPanel(
            selected = "EN",
            onSelect = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
