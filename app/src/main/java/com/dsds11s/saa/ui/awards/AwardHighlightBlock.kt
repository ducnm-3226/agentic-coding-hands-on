package com.dsds11s.saa.ui.awards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Design tokens — Figma mms_B_Highlight (6885:10675) / mms_B.1_header (6885:10676)
private val EyebrowColor = Color(0xFFFFFFFF) // "Sun* Annual Awards 2025"
private val DividerColor = Color(0xFF2E3940)
private val TitleColor = Color(0xFFFFEA9E) // "Hệ thống giải thưởng SAA 2025"
private val DropdownBorderColor = Color(0xFF998C5F)
private val DropdownBg = Color(0x1AFFEA9E) // rgba(FFEA9E, 10%)
private val DropdownTextColor = Color(0xFFFFFFFF)
private val DropdownMenuBg = Color(0xFF0F2030)
private val DropdownMenuItemText = Color(0xFFFFFFFF)
private val DropdownMenuSelectedText = Color(0xFFFFEA9E)
private val ChevronTint = Color(0xFFFFEA9E)

/**
 * Award highlight block — Figma mms_B_Highlight (6885:10675).
 *
 * Displays the keyvisual artwork background, the section header ("Sun* Annual Awards 2025" +
 * "Hệ thống giải thưởng SAA 2025"), and a dropdown that lists all awards and calls
 * [onAwardSelected] with the selected award's id.
 *
 * @param currentAward     The currently selected award (drives dropdown label)
 * @param awards           Full list of awards to populate the dropdown
 * @param onAwardSelected  Invoked with award.id when the user picks a different award
 */
@Composable
fun AwardHighlightBlock(
    currentAward: Award,
    awards: List<Award>,
    onAwardSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Content-only: the keyvisual artwork is provided by the parent (AwardDetailScreen)
    // as a single top band spanning header + KV banner + this block, matching the Home hero.
    AwardSectionHeader(
        currentAward = currentAward,
        awards = awards,
        onAwardSelected = onAwardSelected,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
    )
}

@Composable
private fun AwardSectionHeader(
    currentAward: Award,
    awards: List<Award>,
    onAwardSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Eyebrow — "Sun* Annual Awards 2025"
        Text(
            text = stringResource(R.string.award_highlight_sub_label),
            color = EyebrowColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )

        // Thin divider — node 6885:10676 equivalent of HomeAwards divider
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DividerColor,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Section title — "Hệ thống giải thưởng SAA 2025"
        Text(
            text = stringResource(R.string.award_highlight_section_title),
            color = TitleColor,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown selector showing current award name
        Box {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(DropdownBg)
                        .border(1.dp, DropdownBorderColor, RoundedCornerShape(4.dp))
                        .clickable { expanded = true }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = currentAward.name,
                    color = DropdownTextColor,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                )
                Image(
                    painter = painterResource(R.drawable.login_ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(ChevronTint),
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(DropdownMenuBg),
            ) {
                awards.forEach { award ->
                    val isSelected = award.id == currentAward.id
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = award.name,
                                color = if (isSelected) DropdownMenuSelectedText else DropdownMenuItemText,
                                fontSize = 14.sp,
                                fontFamily = Montserrat,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                            )
                        },
                        onClick = {
                            expanded = false
                            onAwardSelected(award.id)
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun AwardHighlightBlockPreview() {
    SAATheme {
        val awards = LocalAwardRepository.all()
        AwardHighlightBlock(
            currentAward = awards.first(),
            awards = awards,
            onAwardSelected = {},
        )
    }
}
