package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Design tokens — Figma [iOS] Sun*Kudos_View kudo (6885:10128)
private val CardBorder = Color(0xFFFFEA9E)
private val TimestampColor = Color(0xFF999999)
private val TextDark = Color(0xFF00101A)
private val ThumbPlaceholder = Color(0xFF2E3940)

/** Sender → receiver row with avatars, name + department (B.3). */
@Composable
internal fun DetailTransferRow(
    post: KudosPost,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DetailPersonInfo(name = post.senderName, department = post.department)
        Icon(
            painter = painterResource(R.drawable.kudos_ic_send),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = TextDark,
        )
        DetailPersonInfo(name = post.receiverName, department = post.department)
    }
}

/** Single person card — avatar placeholder + name + department code (B.3.2). */
@Composable
internal fun DetailPersonInfo(
    name: String,
    department: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ThumbPlaceholder)
                    .border(1.5.dp, CardBorder, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name.firstOrNull()?.toString() ?: "?",
                color = CardBorder,
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = name,
            color = TextDark,
            fontSize = 11.sp,
            lineHeight = 15.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = department,
            color = TimestampColor,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
    }
}

/** Attached-image gallery — row of square placeholder thumbnails (F.2, max 5). */
@Composable
internal fun DetailImageGallery(
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(count.coerceAtMost(5)) {
            Box(
                modifier =
                    Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(ThumbPlaceholder),
            )
        }
    }
}
