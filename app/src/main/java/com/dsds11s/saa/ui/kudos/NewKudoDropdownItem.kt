package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen 5MU728Tjck, node 6891:17450
private val DropdownItemSelectedBg = Color(0x33FFEA9E) // rgba(255,234,158,0.20)
private val DropdownNameColor = Color(0xFFFFFFFF)
private val DropdownUnitColor = Color(0xFF999999)
private val AvatarBorderColor = Color(0xFFFFFFFF)

/**
 * Single row in the recipient dropdown.
 * Figma: avatar (40dp circle, white border 1.9dp) + name (white 14sp) + unit (grey 14sp).
 * First item has highlighted background (rgba(255,234,158,0.20)), second is plain.
 */
@Composable
internal fun RecipientDropdownItem(
    recipient: KudoRecipient,
    isFirst: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (isFirst) DropdownItemSelectedBg else Color.Transparent)
                .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar placeholder (40dp circle, white border 1.9dp)
        Box(
            modifier =
                Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEEEEE))
                    .border(1.9.dp, AvatarBorderColor, CircleShape),
        )
        // Name + unit
        Column {
            Text(
                text = recipient.name,
                color = DropdownNameColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = recipient.unit,
                color = DropdownUnitColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
