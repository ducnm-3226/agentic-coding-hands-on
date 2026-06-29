package com.dsds11s.saa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Design tokens — Figma mms_5_kudos (6885:9039)
private val KudosAccentBar = Color(0xFF2E3940) // node I6885:9040;75:1885 rgba(46,57,64,1)
private val KudosTitleColor = Color(0xFFFFFFFF) // "Phong trào ghi nhận" fill
private val KudosSubtitleColor = Color(0xFFFFEA9E) // "Sun* Kudos" fill rgba(255,234,158,1)
private val KudosBannerBg = Color(0xFF0F0F0F) // banner rectangle fallback bg
private val KudosNoteColor = Color(0xFFFFFFFF) // note text fill
private val KudosButtonBg = Color(0xFFFFEA9E) // button bg rgba(255,234,158,1)
private val KudosButtonText = Color(0xFF00101A) // label fill rgba(0,16,26,1)

/** Kudos section — Figma mms_5_kudos (6885:9039). Presentational; interaction via [onDetailClick]. */
@Composable
fun HomeKudos(
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        KudosSectionHeader()
        KudosBanner()
        KudosNote()
        KudosDetailButton(onClick = onDetailClick)
    }
}

// Section header — node mms_5.1_header (6885:9040)
@Composable
private fun KudosSectionHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(R.string.home_kudos_section_title),
            color = KudosTitleColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        // Accent divider — 1dp height, color #2E3940 (node I6885:9040;75:1885)
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(KudosAccentBar),
        )
        // Sub-title "Sun* Kudos" in a Row (Frame 488, gap 32dp, height 28dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.home_kudos_subtitle),
                color = KudosSubtitleColor,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

// Banner — node mms_5.2_mm_media_Sunkudos (6885:9041): bg PNG (6885:9043) + logo (6885:9045)
@Composable
private fun KudosBanner(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(145.dp)
                .clip(RoundedCornerShape(4.65.dp))
                .background(KudosBannerBg),
    ) {
        Image(
            painter = painterResource(R.drawable.home_kudos_banner_bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
        // Logo node 6885:9045: 118x21dp, right-aligned, vertically centered.
        // Banner w=335 (x20→355), logo ends at x=333 → 22dp right padding.
        Image(
            painter = painterResource(R.drawable.home_kudos_logo),
            contentDescription = "Sun* Kudos",
            modifier =
                Modifier
                    .size(width = 118.dp, height = 21.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 22.dp),
        )
    }
}

// Note paragraph — node 6885:9053
@Composable
private fun KudosNote(modifier: Modifier = Modifier) {
    val annotated =
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                append(stringResource(R.string.home_kudos_note_heading))
            }
            append("\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                append(stringResource(R.string.home_kudos_note_body))
            }
        }
    Text(
        text = annotated,
        color = KudosNoteColor,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = Montserrat,
        letterSpacing = 0.25.sp,
        modifier = modifier.fillMaxWidth(),
    )
}

// "Chi tiết" button — node mms_5.3_Button (6885:9055): 160x40dp, #FFEA9E, rounded 4dp
@Composable
private fun KudosDetailButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .width(160.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(KudosButtonBg)
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.home_kudos_detail_button),
            color = KudosButtonText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.home_kudos_ic_arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun HomeKudosPreview() {
    SAATheme {
        HomeKudos(modifier = Modifier.padding(vertical = 16.dp))
    }
}
