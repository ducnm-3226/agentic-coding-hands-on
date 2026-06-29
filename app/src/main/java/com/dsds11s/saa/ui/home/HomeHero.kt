package com.dsds11s.saa.ui.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Colors from Figma nodes 6885:8979–6885:9027
private val HeroBackground = Color(0xFF00101A)
private val HeroBorderColor = Color(0xFF998C5F) // outline btn border
private val HeroButtonFillBg = Color(0xFFFFEA9E)
private val HeroButtonFillText = Color(0xFF00101A)
private val HeroButtonOutlineBg = Color(0x1AFFEA9E) // rgba(FFEA9E, 10%)
private val HeroButtonOutlineText = Color.White
private val HeroGoldText = Color(0xFFFFEA9E)
private val HeroWhiteText = Color.White

/**
 * Home hero section (mm_media_bg 6885:8979 + mms_2_content 6885:8983).
 * Presentational only — stateless countdown. Does NOT include HomeHeader.
 * The orchestrator overlays HomeHeader via Box stacking.
 *
 * NOTE: home_hero_bg.png must be placed in drawable before building.
 * Run plans/260609-1319-home-page/download_assets.sh to fetch it.
 * Compile-safe stand-in (R.drawable.login_keyvisual_bg) is used until then.
 */
@Composable
fun HomeHero(
    modifier: Modifier = Modifier,
    days: Int,
    hours: Int,
    minutes: Int,
    seconds: Int,
    isEnded: Boolean = false,
    onAboutMoreClick: () -> Unit = {},
    onJoinClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxWidth().background(HeroBackground)) {
        // Keyvisual art + gradient — shared with the Award detail screen.
        HeroKeyvisualBackground(modifier = Modifier.matchParentSize())
        // Content column — x20, y144, gap 32dp (mms_2_content 6885:8983)
        Column(
            modifier = Modifier.padding(start = 20.dp, top = 144.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            // (a) RootFuther logo 247x109dp — node 6885:8984
            Image(
                painter = painterResource(R.drawable.login_logo_rootfuther),
                contentDescription = null,
                modifier = Modifier.size(width = 247.dp, height = 109.dp),
            )
            // (b) Frame 553 — countdown + event info
            HeroInfoSection(days = days, hours = hours, minutes = minutes, seconds = seconds, isEnded = isEnded)
            // (c) Action buttons
            HeroButtons(onAboutMoreClick = onAboutMoreClick, onJoinClick = onJoinClick)
        }
    }
}

@Composable
private fun HeroInfoSection(
    days: Int,
    hours: Int,
    minutes: Int,
    seconds: Int,
    isEnded: Boolean,
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        // Countdown time — node 6885:8986
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                if (isEnded) {
                    stringResource(R.string.home_hero_event_occurred)
                } else {
                    stringResource(R.string.home_hero_coming_soon)
                },
                color = HeroWhiteText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Light,
            )
            CountdownTimer(days = days, hours = hours, minutes = minutes, seconds = seconds, isEnded = isEnded)
        }
        // Event info — node 6885:9016
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Time row — node 6885:9017
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.home_hero_time_label),
                    color = HeroWhiteText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    "26/12/2026",
                    color = HeroGoldText,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                )
            }
            // Location row — node 6885:9020
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.home_hero_location_label),
                    color = HeroWhiteText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Light,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Âu Cơ Art Center",
                    color = HeroGoldText,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                )
            }
            // Description — node 6885:9024
            Text(
                text = stringResource(R.string.home_hero_live_stream_note),
                color = HeroWhiteText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

/** Two CTA buttons — nodes 6885:9026 (filled) + 6885:9027 (outline) */
@Composable
private fun HeroButtons(
    onAboutMoreClick: () -> Unit,
    onJoinClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        // Filled: #FFEA9E bg, #00101A text — node 6885:9026
        Row(
            modifier =
                Modifier.weight(1f).height(40.dp).clip(RoundedCornerShape(4.dp))
                    .background(HeroButtonFillBg).clickable(onClick = onAboutMoreClick)
                    .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.home_hero_about_award_button),
                color = HeroButtonFillText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.width(8.dp))
            Image(painterResource(R.drawable.home_hero_ic_arrow_right), null, Modifier.size(24.dp))
        }
        // Outline: #998C5F border, rgba(FFEA9E,10%) bg, white text — node 6885:9027
        Row(
            modifier =
                Modifier.weight(1f).height(40.dp).clip(RoundedCornerShape(4.dp))
                    .background(HeroButtonOutlineBg)
                    .border(1.dp, HeroBorderColor, RoundedCornerShape(4.dp))
                    .clickable(onClick = onJoinClick).padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.home_hero_about_kudos_button),
                color = HeroButtonOutlineText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.width(8.dp))
            Image(painterResource(R.drawable.home_hero_ic_arrow_right_white), null, Modifier.size(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun HomeHeroPreview() {
    SAATheme {
        HomeHero(days = 20, hours = 14, minutes = 35, seconds = 8)
    }
}
