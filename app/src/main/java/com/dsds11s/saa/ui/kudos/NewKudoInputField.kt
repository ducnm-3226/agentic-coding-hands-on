package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen PV7jBVZU1N (shared field + checkbox palette)
private val LabelColor = Color(0xFF00101A)
private val RequiredColor = Color(0xFFCF1322)
private val FieldBorder = Color(0xFF998C5F)
private val FieldBg = Color(0xFFFFFFFF)
private val FieldTextColor = Color(0xFF00101A)
private val FieldPlaceholderColor = Color(0xFF999999)
private val HelperTextColor = Color(0xFF999999)
private val CheckboxFillColor = Color(0xFF998C5F) // inner square when ticked

/**
 * Reusable labeled input row: label (left, 0.42f) + single-line text field (right, 0.58f).
 * Used for single-line labeled fields such as "Danh hiệu".
 */
@Composable
internal fun LabeledInputField(
    label: String,
    isRequired: Boolean,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(0.42f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                color = LabelColor,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
            )
            if (isRequired) {
                Text(
                    text = "*",
                    color = RequiredColor,
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 1.dp),
                )
            }
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier =
                Modifier
                    .weight(0.58f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(3.6.dp))
                    .background(FieldBg)
                    .border(0.5.dp, FieldBorder, RoundedCornerShape(3.6.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
            textStyle =
                TextStyle(
                    color = FieldTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                ),
            singleLine = true,
            cursorBrush = SolidColor(FieldBorder),
            decorationBox = { inner ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = FieldPlaceholderColor,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    inner()
                }
            },
        )
    }
}

/**
 * "Send anonymously" toggle: a 24dp checkbox (golden inner square when ticked) + label.
 * Figma node 6885:9993.
 */
@Composable
internal fun AnonymousToggleRow(
    checked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(FieldBg)
                    .border(1.dp, FieldBorder, RoundedCornerShape(2.dp))
                    .clickable(onClick = onToggle),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Box(
                    modifier =
                        Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(CheckboxFillColor),
                )
            }
        }
        Text(
            text = stringResource(R.string.newkudo_anonymous_label),
            color = HelperTextColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )
    }
}
