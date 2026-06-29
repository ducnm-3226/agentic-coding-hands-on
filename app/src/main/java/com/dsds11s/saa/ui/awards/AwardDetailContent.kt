package com.dsds11s.saa.ui.awards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Design tokens — Figma award content block (node 6885:10684 area)
private val ContentBg = Color(0xFF00101A)
private val DescriptionColor = Color(0xFFFFFFFF)
private val DividerColor = Color(0xFF2E3940)

/**
 * Award detail content block — Figma node 6885:10684 "award".
 *
 * Renders top → bottom:
 *   1. Procedural gold-glow trophy placeholder (raster unavailable — see AwardDetailWidgets)
 *   2. Award title row  (target icon + award.name)
 *   3. Description paragraph
 *   4. Divider
 *   5. Quantity row     (diamond icon + "Số lượng giải thưởng" + value + unit)
 *   6. Individual prize (pin icon + value + "cho giải cá nhân") — shown when non-null
 *   7. Team prize       (pin icon + value + "cho giải tập thể")  — shown when non-null
 *
 * Private sub-composables live in AwardDetailWidgets.kt to keep each file ≤ 200 lines.
 */
@Composable
fun AwardDetailContent(
    award: Award,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(ContentBg)
                .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // 1. Trophy / medal placeholder
        AwardTrophyPlaceholder(awardId = award.id)

        // 2. Award title with icon
        AwardTitleRow(name = award.name)

        // 3. Description paragraph
        Text(
            text = award.description,
            color = DescriptionColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Light,
            letterSpacing = 0.25.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        HorizontalDivider(thickness = 1.dp, color = DividerColor)

        // 4. Quantity row — always shown
        AwardStatRow(
            iconRes = R.drawable.award_ic_diamond,
            label = stringResource(R.string.award_stat_quantity_label),
            value = award.quantity,
            note = award.quantityUnit,
        )

        // 5. Individual prize — only when non-null
        if (award.individualPrize != null) {
            AwardStatRow(
                iconRes = R.drawable.award_ic_pin,
                label = stringResource(R.string.award_stat_prize_label),
                value = award.individualPrize,
                note = stringResource(R.string.award_prize_note_individual),
            )
        }

        // 6. Team prize — only when non-null
        if (award.teamPrize != null) {
            AwardStatRow(
                iconRes = R.drawable.award_ic_pin,
                label = stringResource(R.string.award_stat_prize_label),
                value = award.teamPrize,
                note = stringResource(R.string.award_prize_note_team),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun AwardDetailContentPreview() {
    SAATheme {
        AwardDetailContent(award = LocalAwardRepository.all().first())
    }
}
