package com.dsds11s.saa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Design tokens — Figma node mms_6_float button (6885:9058)
// Pill button: 89x48dp, bg #FFEA9E, corners 100dp (pill), glow box-shadow 0 0 6px #FAE287
private val FabBackground = Color(0xFFFFEA9E) // bg rgba(255,234,158,1)
private val FabIconTint = Color(0xFF00101A) // pen + "/" color rgba(0,16,26,1)
private val FabGlowColor = Color(0xFFFAE287) // glow shadow color #FAE287

/**
 * Floating action button (pill) — Figma node mms_6_float button (6885:9058).
 * 89x48dp pill shape, bg #FFEA9E, glow shadow 0 0 6px #FAE287.
 * Contains: pen icon (24dp) + "/" separator (24sp Montserrat) + Kudos K-lightning icon (24dp).
 * Positioned absolutely in the parent layout at x=266, y=1790 (above nav bar).
 *
 * @param onClick  callback for tap
 */
@Composable
fun HomeKudosFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .size(width = 89.dp, height = 48.dp)
                // Approximate CSS box-shadow: 0 0 6px #FAE287 → use shadow with elevation
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(100.dp),
                    ambientColor = FabGlowColor,
                    spotColor = FabGlowColor,
                )
                .clip(RoundedCornerShape(100.dp))
                .background(FabBackground)
                .clickable(onClick = onClick)
                .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Pen icon — node I6885:9058;75:2164 (null URL, vector drawable authored)
        Image(
            painter = painterResource(R.drawable.home_fab_ic_pen),
            contentDescription = stringResource(R.string.home_fab_write_kudos_desc),
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(FabIconTint),
        )
        // "/" separator — node I6885:9058;75:2165, 9dp wide, 24sp, Montserrat Regular
        Text(
            text = "/",
            color = FabIconTint,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
        // Kudos Sun* flash logo — node I6885:9058;75:2166. Actual Sun* "S" flash mark
        // (cropped from the brand logo asset), shown in its native red — matches design.
        Image(
            painter = painterResource(R.drawable.home_fab_ic_sun_flash),
            contentDescription = stringResource(R.string.home_fab_kudos_desc),
            modifier = Modifier.size(24.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A)
@Composable
private fun HomeKudosFabPreview() {
    SAATheme {
        HomeKudosFab(modifier = Modifier.padding(16.dp))
    }
}
