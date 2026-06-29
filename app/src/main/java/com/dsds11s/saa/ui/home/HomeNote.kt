package com.dsds11s.saa.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Screen-scoped color tokens extracted from Figma node 6885:9029 (text fill = backgroundColor).
private val NoteTextColor = Color(0xFFFFFFFF)

/**
 * "Root Further" paragraph note — node mms_3_note (6885:9028).
 * Presentational only. 333dp wide, 240dp tall at x=20 y=637 on the home canvas.
 */
@Composable
fun HomeNote(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.home_note_body),
        color = NoteTextColor,
        fontSize = 14.sp,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Light,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375)
@Composable
private fun HomeNotePreview() {
    SAATheme {
        HomeNote(modifier = Modifier.padding(horizontal = 20.dp))
    }
}
