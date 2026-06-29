package com.dsds11s.saa.ui.locale

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

/** Flag drawable for a language (VN flag reuses the existing login asset). */
private fun AppLanguage.flagRes(): Int =
    when (this) {
        AppLanguage.VIETNAMESE -> R.drawable.login_ic_vn_flag
        AppLanguage.ENGLISH -> R.drawable.locale_ic_en_flag
    }

/**
 * Shared language selector used by the Home header and the Login screen.
 *
 * Self-contained: reads the current language from [LocalAppLanguage], switches it via
 * [LocalSetAppLanguage], and shows the [LanguageDropdownMenu]. The trigger shows the active
 * flag + code + chevron. Switching recomposes the whole app instantly (see [LocalizedApp]).
 */
@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val current = LocalAppLanguage.current
    val setLanguage = LocalSetAppLanguage.current
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { expanded = true }
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(current.flagRes()),
                    contentDescription = current.code,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = current.code,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                )
            }
            Image(
                painter = painterResource(R.drawable.login_ic_chevron_down),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }

        LanguageDropdownMenu(
            expanded = expanded,
            selected = current.code,
            onSelect = { code ->
                setLanguage(AppLanguage.fromCode(code))
                expanded = false
            },
            onDismiss = { expanded = false },
        )
    }
}
