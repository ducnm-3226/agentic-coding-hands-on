package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen aKWA2klsnt node 6891:17706 (shares the gold border palette)
private val ChipBorder = Color(0xFF998C5F)
private val ChipBg = Color(0xFFFFFFFF)
private val ChipTextColor = Color(0xFF00101A)
private val DropdownItemSelectedBg = Color(0x33FFEA9E) // rgba(255,234,158,0.20)
private val DropdownTextColor = Color(0xFFFFFFFF)
private val CheckColor = Color(0xFFFFEA9E)

/** Wrapping rows of hashtag chips (up to 2 per row), each with a remove button. */
@Composable
internal fun HashtagChipRow(
    chips: List<String>,
    onRemove: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        chips.chunked(2).forEach { rowChips ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowChips.forEach { tag ->
                    HashtagChip(tag = "#$tag", onRemove = { onRemove(tag) })
                }
            }
        }
    }
}

/** Individual chip: text + close icon. Figma node 6885:9951 (border #998C5F, bg white, h32dp). */
@Composable
private fun HashtagChip(
    tag: String,
    onRemove: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .height(32.dp)
                .clip(RoundedCornerShape(3.6.dp))
                .background(ChipBg)
                .border(0.5.dp, ChipBorder, RoundedCornerShape(3.6.dp))
                .padding(start = 3.6.dp, end = 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = tag,
            color = ChipTextColor,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        // Larger touch area (24dp) around the 16dp glyph for an easier tap target.
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .clickable(onClick = onRemove),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.kudos_ic_close),
                contentDescription = stringResource(R.string.newkudo_remove_hashtag, tag),
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(ChipBorder),
            )
        }
    }
}

/**
 * Single row in the hashtag dropdown.
 * Selected rows: highlighted bg + check icon. Figma: h40dp, selected bg rgba(255,234,158,0.20).
 */
@Composable
internal fun HashtagDropdownItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (isSelected) DropdownItemSelectedBg else Color.Transparent)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            color = DropdownTextColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        if (isSelected) {
            Image(
                painter = painterResource(R.drawable.kudos_ic_check),
                contentDescription = stringResource(R.string.newkudo_selected),
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(CheckColor),
            )
        }
    }
}
