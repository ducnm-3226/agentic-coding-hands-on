package com.dsds11s.saa.ui.awards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.ui.theme.Montserrat

/**
 * Per-award trophy artwork, reusing the Home awards-section images where they exist
 * (Top Talent / Top Project / Top Project Leader). Awards without a Home image
 * (Signature-Creator, MVP, Best Manager) fall back to a procedural gold-glow placeholder.
 */
@Composable
internal fun AwardTrophyPlaceholder(
    awardId: String,
    modifier: Modifier = Modifier,
) {
    val trophyRes = awardTrophyRes(awardId)
    if (trophyRes != null) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(160.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(trophyRes),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit,
            )
        }
        return
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(140.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Outer radial glow (120dp)
        Box(
            modifier =
                Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            0f to Color(0xFFFFEA9E).copy(alpha = 0.18f),
                            1f to Color(0x00FFEA9E),
                        ),
                    ),
        )
        // Mid ring (88dp)
        Box(
            modifier =
                Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0x33FFEA9E)),
        )
        // Inner circle with award initials (64dp)
        Box(
            modifier =
                Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            0f to Color(0xFFFFEA9E).copy(alpha = 0.35f),
                            1f to Color(0xFF998C5F).copy(alpha = 0.6f),
                        ),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = awardInitials(awardId),
                color = Color(0xFF00101A),
                fontSize = 22.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
