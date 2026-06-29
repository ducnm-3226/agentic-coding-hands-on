package com.dsds11s.saa.ui.kudos

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen PV7jBVZU1N, node 6885:9976
private val LabelColor = Color(0xFF00101A)
private val ThumbBg = Color(0xFFFFFFFF)
private val ThumbBorder = Color(0xFF998C5F) // var(--Details-Border)
private val RemoveBg = Color(0xFFCF1322) // red remove button
private val AddBtnBg = Color(0xFFFFFFFF)
private val AddBtnBorder = Color(0xFF998C5F)
private val AddBtnTextColor = Color(0xFF00101A)

/**
 * Image row: label "Image" + thumbnails of picked images + "+ Image" button.
 * Spec 7fFAb-K35a (F/F.5): tapping "+ Image" opens the device photo picker; each thumbnail
 * has an ✕ to remove. Max 5 images; the add button is hidden once 5 are attached.
 */
@Composable
fun NewKudoImageRow(
    images: List<Uri>,
    onRemove: (Uri) -> Unit,
    onAddImage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = stringResource(R.string.newkudo_image_label),
            color = LabelColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier =
                Modifier
                    .weight(0.42f)
                    .padding(top = 8.dp),
        )

        Column(
            modifier = Modifier.weight(0.58f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (images.isNotEmpty()) {
                val shown = images.take(KUDO_IMAGE_MAX)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    shown.forEachIndexed { index, uri ->
                        ImageThumbnail(
                            uri = uri,
                            label = stringResource(R.string.newkudo_attached_image, index + 1, shown.size),
                            onRemove = { onRemove(uri) },
                        )
                    }
                }
            }

            // Add button — opens the photo picker; hidden once the max is reached (spec F.5).
            if (images.size < KUDO_IMAGE_MAX) {
                Row(
                    modifier =
                        Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(3.6.dp))
                            .background(AddBtnBg)
                            .border(0.5.dp, AddBtnBorder, RoundedCornerShape(3.6.dp))
                            .clickable(onClick = onAddImage)
                            .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.kudos_ic_plus),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        colorFilter = ColorFilter.tint(AddBtnTextColor),
                    )
                    Text(
                        text = stringResource(R.string.newkudo_image_add_btn),
                        color = AddBtnTextColor,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

/** 32dp rounded thumbnail of a picked image + a small red ✕ remove badge (top-right). */
@Composable
private fun ImageThumbnail(
    uri: Uri,
    label: String,
    onRemove: () -> Unit,
) {
    val thumbnail = rememberImageThumbnail(uri)
    Box(modifier = Modifier.size(40.dp)) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ThumbBg)
                    .border(0.5.dp, ThumbBorder, RoundedCornerShape(8.dp)),
        ) {
            thumbnail?.let {
                Image(
                    bitmap = it,
                    contentDescription = label,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        // Remove badge — visible 14dp dot inside a larger (24dp) touch target, top-right.
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable(onClick = onRemove),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(RemoveBg),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.kudos_ic_close),
                    contentDescription = stringResource(R.string.newkudo_remove_image),
                    modifier = Modifier.size(10.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }
        }
    }
}
