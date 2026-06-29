package com.dsds11s.saa.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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

// Color tokens for the awards section — extracted from Figma nodes 6885:9030..9035.
private val AwardsSectionLabelColor = Color(0xFFFFFFFF) // "Sun* Annual Awards 2025" label
private val AwardsDividerColor = Color(0xFF2E3940) // Rectangle 26 (rgba 46,57,64)
private val AwardsSectionTitleColor = Color(0xFFFFEA9E) // "Hệ thống giải thưởng" title

/**
 * Awards section — node mms_4_awards (6885:9030).
 * Header: "Sun* Annual Awards 2025" eyebrow + "Hệ thống giải thưởng" title.
 * Body: horizontally-scrollable row of 3 award cards, 160dp wide each, gap 24dp.
 * Presentational only — [onAwardClick] receives the 0-based card index.
 *
 * Assets required (place in app/src/main/res/drawable/):
 *   home_award_card1_bg.png   — I6885:9033;72:2115;72:2079 (160×160)
 *   home_award_card1_name.png — I6885:9033;72:2115;72:2080;10:951 "Top Talent" logotype
 *   home_award_card2_bg.png   — I6885:9034;72:2115;72:2085 (160×160)
 *   home_award_card2_name.png — I6885:9034;72:2115;72:2104;214:654 "Top Project" logotype
 *   home_award_card3_bg.png   — I6885:9035;72:2115;75:1549;81:2442 (160×160)
 *   home_award_ic_chevron_right.xml — vector drawable (already created)
 */
@Composable
fun HomeAwards(
    modifier: Modifier = Modifier,
    onAwardClick: (Int) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AwardsSectionHeader()
        Spacer(Modifier.height(24.dp))
        AwardsCardRow(onAwardClick = onAwardClick)
    }
}

@Composable
private fun AwardsSectionHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        // Eyebrow label — "Sun* Annual Awards 2025" — node I6885:9031;75:1884
        Text(
            text = stringResource(R.string.home_awards_eyebrow),
            color = AwardsSectionLabelColor,
            fontSize = 12.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        )
        Spacer(Modifier.height(4.dp))
        // Thin divider — node I6885:9031;75:1885, h=1dp, color #2E3940
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = AwardsDividerColor,
        )
        Spacer(Modifier.height(4.dp))
        // Section title — node I6885:9031;75:1887, "Hệ thống giải thưởng"
        Text(
            text = stringResource(R.string.home_awards_section_title),
            color = AwardsSectionTitleColor,
            fontSize = 22.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        )
    }
}

@Composable
private fun AwardsCardRow(onAwardClick: (Int) -> Unit) {
    // Cards are 160dp wide with 24dp gaps; total content width ~528dp > screen width → horizontal scroll.
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        awards.forEach { data ->
            AwardCard(
                data = data,
                onCardClick = onAwardClick,
            )
        }
    }
}

/**
 * Static award card data extracted verbatim from Figma design nodes.
 * Text content from character properties; images from MM_MEDIA nodes.
 */
private val awards =
    listOf(
        AwardCardData(
            index = 0,
            picRes = R.drawable.home_award_card1_bg,
            nameLabelRes = R.drawable.home_award_card1_name,
            titleText = "Top Talent",
            descText = "Giải thưởng Top Talent vinh danh những cá nhân xuất sắc trên mọi phương diện",
        ),
        AwardCardData(
            index = 1,
            picRes = R.drawable.home_award_card2_bg,
            nameLabelRes = R.drawable.home_award_card2_name,
            titleText = "Top Project",
            descText = "Giải thưởng Top Project vinh danh các tập thể dự án xuất sắc",
        ),
        AwardCardData(
            index = 2,
            picRes = R.drawable.home_award_card3_bg,
            nameLabelRes = null,
            titleText = "Top Project Leader",
            descText = "Giải thưởng Top Project Leader vinh danh những nhà lãnh đạo dự án xuất sắc",
        ),
    )

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun HomeAwardsPreview() {
    SAATheme {
        HomeAwards(modifier = Modifier.padding(horizontal = 20.dp))
    }
}
