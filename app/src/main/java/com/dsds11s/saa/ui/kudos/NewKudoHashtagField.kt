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

// Figma tokens — screen PV7jBVZU1N node 6885:9936, screen aKWA2klsnt node 6891:17706
private val LabelColor = Color(0xFF00101A)
private val RequiredColor = Color(0xFFCF1322)
private val AddBtnBorder = Color(0xFF998C5F)
private val AddBtnBg = Color(0xFFFFFFFF)
private val AddBtnTextColor = Color(0xFF00101A)
private val DropdownBg = Color(0xFF00070C)

/**
 * Hashtag section: label + chips + add button, with the tag list in a [DropdownMenu] (Popup)
 * so it overlays content below instead of pushing it down. Max 5 hashtags.
 * Figma nodes 6885:9936 (form) + 6891:17706 (dropdown screen aKWA2klsnt).
 */
@Composable
fun NewKudoHashtagField(
    selected: List<String>,
    dropdownOpen: Boolean,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit,
    onRemove: (String) -> Unit,
    onToggle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var anchorWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            // Label + required asterisk
            Row(
                modifier = Modifier.weight(0.42f).padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.newkudo_hashtag_label),
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

            // Chips + add button, with the tag list anchored as a Popup below
            Box(modifier = Modifier.weight(0.58f)) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .onSizeChanged { anchorWidthPx = it.width },
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    if (selected.isNotEmpty()) {
                        HashtagChipRow(chips = selected, onRemove = onRemove)
                    }

                    if (selected.size < 5) {
                        Row(
                            modifier =
                                Modifier
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(3.6.dp))
                                    .background(AddBtnBg)
                                    .border(0.5.dp, AddBtnBorder, RoundedCornerShape(3.6.dp))
                                    .clickable(onClick = onAddClick)
                                    .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.kudos_ic_plus),
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                colorFilter = ColorFilter.tint(AddBtnTextColor),
                            )
                            Text(
                                text = stringResource(R.string.newkudo_hashtag_add_btn),
                                color = AddBtnTextColor,
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = dropdownOpen,
                    onDismissRequest = onDismiss,
                    modifier =
                        Modifier
                            .width(with(density) { anchorWidthPx.toDp() })
                            .background(DropdownBg),
                ) {
                    allHashtags.forEach { tag ->
                        HashtagDropdownItem(
                            name = "#$tag",
                            isSelected = selected.contains(tag),
                            onClick = { onToggle(tag) },
                        )
                    }
                }
            }
        }
    }
}
