package com.dsds11s.saa.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Color tokens for award cards — from Figma node 6885:8051 card component.
internal val AwardPictureBorder = Color(0xFFFFEA9E) // #FFEA9E — Details-Text-Primary-1
internal val AwardTitleColor = Color(0xFFFFEA9E) // same primary yellow
internal val AwardDescColor = Color(0xFFFFFFFF)
internal val AwardButtonTextColor = Color(0xFFFFFFFF)

/**
 * Data class describing one award card as extracted from the Figma design.
 *
 * @param index         0-based position; passed back via [onCardClick]
 * @param picRes        drawable res for the 160×160 picture background image
 * @param nameLabelRes  drawable res for the award name image (e.g. "Top Talent" logotype),
 *                      null if the award uses text only (card 3 / Top Project Leader)
 * @param titleText     award title string (14sp yellow, Medium)
 * @param descText      award description (14sp white, Light, 3 lines max)
 */
data class AwardCardData(
    val index: Int,
    @DrawableRes val picRes: Int,
    @DrawableRes val nameLabelRes: Int?,
    val titleText: String,
    val descText: String,
)

/**
 * Single award card — node component 6885:8051.
 * 160dp wide × 298dp tall, column layout, gap 12dp.
 * Presentational only; click reported via [onCardClick].
 */
@Composable
internal fun AwardCard(
    data: AwardCardData,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .width(160.dp)
                .wrapContentHeight()
                .clickable { onCardClick(data.index) },
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Picture area — 160×160dp, rounded 11dp, 0.5dp golden border
        AwardPicture(
            picRes = data.picRes,
            nameLabelRes = data.nameLabelRes,
        )

        // Title + description block
        Column(
            modifier = Modifier.width(160.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = data.titleText,
                color = AwardTitleColor,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = data.descText,
                color = AwardDescColor,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Light,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }

        // "Chi tiết" text button with chevron right
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.home_award_detail_button),
                color = AwardButtonTextColor,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
            )
            Image(
                painter = painterResource(R.drawable.home_award_ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

/**
 * 160×160dp award picture with rounded corners, golden border, and
 * an overlaid award-name logotype image (if provided).
 */
@Composable
private fun AwardPicture(
    @DrawableRes picRes: Int,
    @DrawableRes nameLabelRes: Int?,
) {
    val pictureShape = RoundedCornerShape(11.dp)
    Box(
        modifier =
            Modifier
                .size(160.dp)
                .clip(pictureShape)
                .border(width = 0.5.dp, color = AwardPictureBorder, shape = pictureShape),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // Full-bleed award background image
        Image(
            painter = painterResource(picRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(160.dp),
        )
        // Award name logotype image overlaid near center (roughly y = center-ish per Figma)
        if (nameLabelRes != null) {
            Image(
                painter = painterResource(nameLabelRes),
                contentDescription = null,
                modifier =
                    Modifier
                        .width(105.dp)
                        .height(17.dp)
                        .align(Alignment.Center),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
