package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma B.6. Spotlight board (6885:9099), mms_B.7_Spotlight (6885:9101)
private val EyebrowColor = Color(0xFFFFFFFF)
private val SectionDivider = Color(0xFF2E3940)
private val TitleColor = Color(0xFFFFEA9E)
private val SpotlightBg = Color(0xFF001018) // approximate dark bg for graph area
private val KudosCountColor = Color(0xFFFFEA9E) // "388 KUDOS" fill
private val SearchBg = Color(0xFF00070C)
private val SearchBorder = Color(0xFF998C5F)
private val SearchHintColor = Color(0xFF666666)
private val SearchTextColor = Color(0xFFFFFFFF)

/**
 * SPOTLIGHT BOARD section — Figma B.6 (6885:9099).
 * Title header + static network-graph placeholder + search field.
 * Graph is a STATIC dark placeholder box (no graph engine per scope decision).
 * Search field is a focusable real TextField covering the "Searching" state.
 */
@Composable
fun KudosSpotlightBoard(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Section header — node 6885:9100, same pattern as Highlight header
        SpotlightHeader()

        // Spotlight content card — node mms_B.7_Spotlight (6885:9101)
        SpotlightContent(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
        )
    }
}

@Composable
private fun SpotlightHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Eyebrow "Sun* Annual Awards 2025" — node I6885:9100;75:1884
        Text(
            text = stringResource(R.string.kudos_eyebrow_event),
            color = EyebrowColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        // Divider
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SectionDivider),
        )
        // Title "SPOTLIGHT BOARD" — node I6885:9100;75:1887
        Text(
            text = stringResource(R.string.kudos_section_spotlight_title),
            color = TitleColor,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun SpotlightContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // "388 KUDOS" label — node mms_B.7.1_388 KUDOS (6885:9219)
        Text(
            text = stringResource(R.string.kudos_spotlight_kudos_count),
            color = KudosCountColor,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
        )

        // Static network-graph placeholder — node mms_B.7.2_Pan zoom (6885:9217) + image 24 (6885:9103)
        // Per scope: Spotlight network chart = STATIC dark placeholder box (no graph engine)
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SpotlightBg),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.kudos_spotlight_network_placeholder),
                color = Color(0x44FFEA9E),
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
        }

        // Search field — node mms_B.7.3_Tìm kiếm sunner (6885:9216)
        // Real focusable TextField so it covers the "Searching" state visually
        SpotlightSearchField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
        )
    }
}

/** Tìm kiếm sunner search field — Figma node 6885:9216 / I6885:9216;186:2758. */
@Composable
private fun SpotlightSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle =
            TextStyle(
                color = SearchTextColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            ),
        cursorBrush = SolidColor(TitleColor),
        modifier =
            modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SearchBg)
                .border(1.dp, SearchBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.kudos_spotlight_search_hint),
                        color = SearchHintColor,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                    )
                }
                innerTextField()
            }
        },
    )
}
