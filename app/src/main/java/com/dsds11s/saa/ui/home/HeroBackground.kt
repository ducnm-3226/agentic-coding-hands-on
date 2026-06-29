package com.dsds11s.saa.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dsds11s.saa.R

private val HeroNavy = Color(0xFF00101A)

/**
 * Shared keyvisual background — Figma node mm_media_bg (6885:8979).
 * Dark navy fill + keyvisual artwork (Crop) + a left→right navy-to-transparent
 * gradient overlay. Used by the Home hero AND the Award detail screen so both
 * share the exact same background treatment (single source of truth).
 *
 * Size it via [modifier] (matchParentSize on Home; a top band on Award detail).
 */
@Composable
fun HeroKeyvisualBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(HeroNavy)) {
        // Keyvisual art — node 6885:8979
        Image(
            painter = painterResource(R.drawable.home_hero_bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
        // Horizontal navy gradient overlay — node 6885:8981 "Shadow Left"
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            0.0f to HeroNavy,
                            0.186f to Color(0xFF10181F),
                            0.772f to Color.Transparent,
                        ),
                    ),
        )
    }
}
