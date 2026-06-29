package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen PV7jBVZU1N, node 6885:9917
private val ToolbarBorder = Color(0xFF998C5F) // var(--Details-Border, #998C5F)
private val ToolbarBg = Color(0x00000000) // transparent
private val ToolbarIconTint = Color(0xFF998C5F) // muted gold
private val TextFieldBg = Color(0xFFFFFFFF)
private val TextFieldText = Color(0xFF00101A)
private val TextFieldPlaceholder = Color(0xFF999999) // rgba(153,153,153,1)
private val CommunityLinkColor = Color(0xFFD4271D) // red link + underline
private val ToolbarActiveBg = Color(0x33FFEA9E) // active toggle highlight

/**
 * Formatting toolbar + multi-line message field. Whole-field formatting (spec C):
 * Bold/Italic/Strikethrough/Link(underline) style the whole text; numbered-list and quote
 * apply line-prefix transforms to the text. Each tool toggles independently.
 */
@Composable
fun NewKudoMessageEditor(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCommunityStandards: () -> Unit = {},
) {
    val toolbarIcons =
        remember {
            listOf(
                R.drawable.kudos_ic_bold to "Bold",
                R.drawable.kudos_ic_italic to "Italic",
                R.drawable.kudos_ic_strike to "Strikethrough",
                R.drawable.kudos_ic_list to "List",
                R.drawable.kudos_ic_link to "Link",
                R.drawable.kudos_ic_quote to "Quote",
            )
        }

    // Independent toggles — indices: 0=Bold,1=Italic,2=Strikethrough,3=List,4=Link,5=Quote.
    // 0/1/2/4 style the whole message via TextStyle; 3 (numbered list) and 5 (quote) apply
    // line-prefix text transforms.
    val activeTools = remember { mutableStateListOf<Int>() }
    val onToolClick: (Int) -> Unit = { idx ->
        val turningOn = idx !in activeTools
        if (turningOn) activeTools.add(idx) else activeTools.remove(idx)
        when (idx) {
            3 -> onValueChange(if (turningOn) toNumberedList(value) else stripNumberedList(value))
            5 -> onValueChange(if (turningOn) toQuotePrefix(value) else stripQuotePrefix(value))
        }
    }

    // Toolbar row — radius top corners only
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(24.dp)
                .border(0.5.dp, ToolbarBorder, RoundedCornerShape(topStart = 3.6.dp, topEnd = 3.6.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left side: 6 icon buttons
        toolbarIcons.forEachIndexed { idx, (iconRes, desc) ->
            Box(
                modifier =
                    Modifier
                        .size(24.dp)
                        .background(if (idx in activeTools) ToolbarActiveBg else ToolbarBg)
                        .clickable { onToolClick(idx) },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = desc,
                    modifier = Modifier.size(14.dp),
                    colorFilter = ColorFilter.tint(ToolbarIconTint),
                )
            }
        }

        // Right side: community standards link. The left edge of this border acts as the
        // vertical divider separating the icon group from the link (intentional).
        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .height(24.dp)
                    .background(ToolbarBg)
                    .border(
                        0.5.dp,
                        ToolbarBorder,
                        RoundedCornerShape(topEnd = 3.6.dp),
                    )
                    .clickable(onClick = onCommunityStandards),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.newkudo_community_standards),
                color = CommunityLinkColor,
                fontSize = 9.sp,
                lineHeight = 13.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                textDecoration = TextDecoration.Underline,
            )
        }
    }

    // Combine strikethrough (idx 2) + link underline (idx 4) decorations.
    val decorations =
        buildList {
            if (2 in activeTools) add(TextDecoration.LineThrough)
            if (4 in activeTools) add(TextDecoration.Underline)
        }
    val messageDecoration =
        when (decorations.size) {
            0 -> null
            1 -> decorations[0]
            else -> TextDecoration.combine(decorations)
        }

    // Text input area — border on sides + bottom, no top border (toolbar has it)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(89.dp)
                .background(
                    TextFieldBg,
                    RoundedCornerShape(bottomStart = 3.6.dp, bottomEnd = 3.6.dp),
                )
                .border(
                    0.5.dp,
                    ToolbarBorder,
                    RoundedCornerShape(bottomStart = 3.6.dp, bottomEnd = 3.6.dp),
                )
                .padding(8.dp),
        textStyle =
            TextStyle(
                color = TextFieldText,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = if (0 in activeTools) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (1 in activeTools) FontStyle.Italic else FontStyle.Normal,
                textDecoration = messageDecoration,
            ),
        cursorBrush = SolidColor(Color(0xFF998C5F)),
        decorationBox = { inner ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.newkudo_message_placeholder),
                        color = TextFieldPlaceholder,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                    )
                }
                inner()
            }
        },
    )
}
