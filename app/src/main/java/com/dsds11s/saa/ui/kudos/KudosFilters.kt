package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — identical to the Awards screen dropdown (AwardHighlightDropdown).
private val FilterBorder = Color(0xFF998C5F) // var(--Details-Border, #998C5F)
private val FilterBg = Color(0x1AFFEA9E) // rgba(255,234,158,0.10)
private val MenuBg = Color(0xFF0A1929) // dark menu surface (same as Awards)
private val FilterPlaceholder = Color(0xFFFFFFFF)
private val FilterSelected = Color(0xFFFFEA9E)

/**
 * Filter row — two dropdown chips (Hashtag + Phòng ban) sharing the row width.
 * Uses the SAME anchored-dropdown component, styling, and chevron icon as the
 * Awards screen (AwardHighlightDropdown): the menu opens directly below the field.
 */
@Composable
fun KudosFilterRow(
    selectedHashtag: String?,
    hashtagOptions: List<String>,
    onHashtagSelected: (String?) -> Unit,
    selectedDepartment: String?,
    departmentOptions: List<String>,
    onDepartmentSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KudosFilterDropdown(
            placeholder = stringResource(R.string.kudos_filter_hashtag),
            selected = selectedHashtag,
            options = hashtagOptions,
            onSelected = onHashtagSelected,
            modifier = Modifier.weight(1f),
        )
        KudosFilterDropdown(
            placeholder = stringResource(R.string.kudos_filter_department),
            selected = selectedDepartment,
            options = departmentOptions,
            onSelected = onDepartmentSelected,
            modifier = Modifier.weight(1f),
        )
    }
}

/**
 * Single label dropdown — trigger (gold-muted border, radius 4dp) + anchored
 * Material3 DropdownMenu that appears directly below the field. Mirrors the Awards
 * dropdown exactly (same border/bg/menu colors + login_ic_chevron_down arrow).
 * Re-selecting the current value clears the filter (passes null).
 */
@Composable
private fun KudosFilterDropdown(
    placeholder: String,
    selected: String?,
    options: List<String>,
    onSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    // Trigger width (px) so the expanded menu can match the field width exactly.
    var triggerWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    Box(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .onSizeChanged { triggerWidthPx = it.width }
                    .clip(RoundedCornerShape(4.dp))
                    .background(FilterBg)
                    .border(1.dp, FilterBorder, RoundedCornerShape(4.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selected ?: placeholder,
                color = if (selected != null) FilterSelected else FilterPlaceholder,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = if (selected != null) FontWeight.Medium else FontWeight.Normal,
                modifier = Modifier.weight(1f),
                maxLines = 1,
            )
            Icon(
                painter = painterResource(R.drawable.login_ic_chevron_down),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = FilterPlaceholder,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =
                Modifier
                    .width(with(density) { triggerWidthPx.toDp() })
                    .background(MenuBg),
        ) {
            options.forEach { option ->
                val isSelected = option == selected
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (isSelected) FilterSelected else FilterPlaceholder,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = Montserrat,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        )
                    },
                    onClick = {
                        expanded = false
                        onSelected(if (isSelected) null else option)
                    },
                )
            }
        }
    }
}
