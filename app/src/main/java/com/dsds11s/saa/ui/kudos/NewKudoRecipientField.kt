package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen PV7jBVZU1N, node 6885:9905
private val FieldBorder = Color(0xFF998C5F)
private val FieldBg = Color(0xFFFFFFFF)
private val FieldTextColor = Color(0xFF00101A)
private val FieldPlaceholderColor = Color(0xFF999999)
private val LabelColor = Color(0xFF00101A)
private val RequiredColor = Color(0xFFCF1322)

// Dropdown background — screen 5MU728Tjck, node 6891:17450
private val DropdownBg = Color(0xFF00070C)

/**
 * Recipient label + search field with an anchored dark dropdown rendered in a [DropdownMenu]
 * (a Popup) so it overlays content below instead of pushing it down. Figma nodes 6885:9905
 * (base) + 6891:17450 (dropdown, screen 5MU728Tjck).
 *
 * Dropdown item rendering lives in NewKudoDropdownItem.kt (same package).
 */
@Composable
fun NewKudoRecipientField(
    label: String,
    selected: KudoRecipient?,
    query: String,
    dropdownOpen: Boolean,
    searchResults: List<KudoRecipient>,
    onFieldClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelect: (KudoRecipient) -> Unit,
    modifier: Modifier = Modifier,
) {
    var triggerWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Label + required asterisk
        Row(
            modifier = Modifier.weight(0.42f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                color = LabelColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = "*",
                color = RequiredColor,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 1.dp),
            )
        }

        // Select field + anchored dropdown (Popup)
        Box(modifier = Modifier.weight(0.58f)) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .onSizeChanged { triggerWidthPx = it.width }
                        .clip(RoundedCornerShape(3.6.dp))
                        .background(FieldBg)
                        .border(0.5.dp, FieldBorder, RoundedCornerShape(3.6.dp))
                        .clickable(onClick = onFieldClick)
                        .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val display = selected?.name ?: query
                val isPlaceholder = display.isEmpty()
                Text(
                    text = if (isPlaceholder) stringResource(R.string.newkudo_recipient_placeholder) else display,
                    color = if (isPlaceholder) FieldPlaceholderColor else FieldTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                )
                Image(
                    painter = painterResource(R.drawable.login_ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(FieldBorder),
                )
            }

            DropdownMenu(
                expanded = dropdownOpen && searchResults.isNotEmpty(),
                onDismissRequest = onDismiss,
                modifier =
                    Modifier
                        .width(with(density) { triggerWidthPx.toDp() })
                        .background(DropdownBg),
            ) {
                searchResults.forEach { recipient ->
                    RecipientDropdownItem(
                        recipient = recipient,
                        isFirst = recipient == searchResults.first(),
                        onClick = { onSelect(recipient) },
                    )
                }
            }
        }
    }
}
