package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma mms_B_Highlight (6885:9084), mms_B.1_header (6885:9085)
internal val HighlightEyebrowColor = Color(0xFFFFFFFF)
private val SectionDivider = Color(0xFF2E3940)
private val TitleColor = Color(0xFFFFEA9E)
internal val NavArrowColor = Color(0xFFFFFFFF)

/** Carousel navigation arrow — reuses the chevron, rotated 180° for the left arrow. */
@Composable
internal fun NavArrow(
    pointsLeft: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(R.drawable.kudos_ic_arrow_right),
        contentDescription =
            if (pointsLeft) {
                stringResource(R.string.kudos_carousel_previous)
            } else {
                stringResource(R.string.kudos_carousel_next)
            },
        tint = NavArrowColor,
        modifier =
            modifier
                .size(28.dp)
                .clickable(onClick = onClick)
                .rotate(if (pointsLeft) 180f else 0f),
    )
}

/** Section header: eyebrow + divider + title + filter row. */
@Composable
internal fun HighlightSectionHeader(
    selectedHashtag: String?,
    selectedDepartment: String?,
    hashtagOptions: List<String>,
    departmentOptions: List<String>,
    onHashtagSelected: (String?) -> Unit,
    onDepartmentSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Eyebrow "Sun* Annual Awards 2025" — node I6885:9086;75:1884, 12sp
        Text(
            text = stringResource(R.string.kudos_eyebrow_event),
            color = HighlightEyebrowColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        // Accent divider — node I6885:9086;75:1885
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SectionDivider),
        )
        // Title "HIGHLIGHT KUDOS" — node I6885:9086;75:1887, 22sp #FFEA9E
        Text(
            text = stringResource(R.string.kudos_section_highlight_title),
            color = TitleColor,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Filter row — node 6885:9087
        KudosFilterRow(
            selectedHashtag = selectedHashtag,
            hashtagOptions = hashtagOptions,
            onHashtagSelected = onHashtagSelected,
            selectedDepartment = selectedDepartment,
            departmentOptions = departmentOptions,
            onDepartmentSelected = onDepartmentSelected,
        )
    }
}
