package com.dsds11s.saa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

// Design tokens — Figma node mms_7_nav bar (6885:9056)
// Nav bar background: rgba(255,234,158,0.15) blur(20px), corners 20/20/0/0.
// Compose can't backdrop-blur cheaply; approximate the frosted look by fading the
// top edge to transparent so it reads as glass, not a solid panel.
private val NavBarBgTransparent = Color(0x00FFEA9E) // top edge — fully transparent
private val NavBarBg = Color(0x26FFEA9E) // base fill rgba(255,234,158,0.15) ≈ 0x26
private val NavActiveColor = Color(0xFFFFEA9E) // active tab label + icon tint
private val NavInactiveColor = Color(0xFFFFFFFF) // inactive tab label + icon tint

private data class NavTab(
    val iconRes: Int,
    val label: String,
)

private val navTabIcons =
    listOf(
        R.drawable.home_nav_ic_home,
        R.drawable.home_nav_ic_awards,
        R.drawable.home_nav_ic_kudos,
        R.drawable.home_nav_ic_profile,
    )

/**
 * Bottom navigation bar — Figma node mms_7_nav bar (6885:9056).
 * 375x72dp, frosted glass bg (rgba(255,234,158,0.15)), top corners 20dp.
 * 4 tabs with 24dp icon + 12sp label. Tab 0 ("SAA 2025") is the active/home tab.
 * Active tab: icon + label in #FFEA9E. Inactive: #FFFFFF.
 * Stateless: drives selection via [selectedIndex] and [onTabSelected].
 *
 * @param selectedIndex  index of the currently selected tab (0–3)
 * @param onTabSelected  callback invoked with the tapped tab index
 */
@Composable
fun HomeBottomNav(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
) {
    val navTabs =
        listOf(
            NavTab(navTabIcons[0], stringResource(R.string.home_nav_tab_home)),
            NavTab(navTabIcons[1], stringResource(R.string.home_nav_tab_awards)),
            NavTab(navTabIcons[2], stringResource(R.string.home_nav_tab_kudos)),
            NavTab(navTabIcons[3], stringResource(R.string.home_nav_tab_profile)),
        )
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(
                    Brush.verticalGradient(
                        0f to NavBarBgTransparent,
                        0.4f to NavBarBg,
                        1f to NavBarBg,
                    ),
                )
                .navigationBarsPadding(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            navTabs.forEachIndexed { index, tab ->
                NavTabItem(
                    tab = tab,
                    isSelected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                )
            }
        }
    }
}

/**
 * Single tab item — icon (24dp) + label (12sp), vertically stacked, gap 4dp.
 * Tint and label color: #FFEA9E when selected, #FFFFFF when inactive.
 */
@Composable
private fun NavTabItem(
    tab: NavTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tint = if (isSelected) NavActiveColor else NavInactiveColor
    Column(
        modifier =
            modifier
                .size(width = 60.dp, height = 44.dp)
                .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
    ) {
        Image(
            painter = painterResource(tab.iconRes),
            contentDescription = tab.label,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(tint),
        )
        Text(
            text = tab.label,
            color = tint,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun HomeBottomNavPreview() {
    SAATheme {
        HomeBottomNav()
    }
}
