package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

/**
 * Top bar for drill-down Kudos screens — back chevron (left) + centered title.
 * Figma: All Kudos (6891:15995) / View Kudo (6885:10128) headers.
 *
 * Transparent background so it floats over the shared keyvisual hero.
 */
@Composable
fun KudosTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        // 40dp clickable box keeps the touch target accessible while the chevron stays 24dp.
        Box(
            modifier =
                Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.kudos_ic_back),
                contentDescription = stringResource(R.string.kudos_back_content_desc),
                modifier = Modifier.size(24.dp),
                tint = Color.White,
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
