package com.dsds11s.saa.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.locale.LanguageSelector
import com.dsds11s.saa.ui.theme.Montserrat
import com.dsds11s.saa.ui.theme.SAATheme

// Screen-scoped colors pulled from the Figma design (screen 8HGlvYGJWq).
private val LoginBackground = Color(0xFF00101A)
private val LoginButtonBackground = Color(0xFFFFEA9E)
private val LoginButtonText = Color(0xFF00101A)

/**
 * [iOS] Login screen rebuilt from the MoMorph/Figma design.
 * Presentational only — login/language callbacks are surfaced as parameters.
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize().background(LoginBackground)) {
        Image(
            painter = painterResource(R.drawable.login_keyvisual_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        // Left-to-right navy fade — recreates the design's dark "opening" on the
        // left where the logo/text sit, with the artwork showing through on the right.
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0.0f to LoginBackground,
                            0.22f to LoginBackground,
                            0.72f to Color.Transparent,
                        ),
                    ),
        )
        // Top scrim so the header reads against the artwork.
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .background(
                        Brush.verticalGradient(
                            0f to LoginBackground.copy(alpha = 0.9f),
                            1f to Color.Transparent,
                        ),
                    ),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp),
        ) {
            LoginHeader(
                onLanguageClick = onLanguageClick,
                modifier = Modifier.padding(top = 8.dp),
            )
            Spacer(Modifier.weight(1.6f))
            Image(
                painter = painterResource(R.drawable.login_logo_rootfuther),
                contentDescription = "ROOT FURTHER",
                modifier = Modifier.size(width = 247.dp, height = 109.dp),
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.login_tagline),
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Light,
            )
            Spacer(Modifier.weight(2f))
            GoogleLoginButton(
                onClick = onLoginClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = stringResource(R.string.login_copyright),
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            )
        }
    }
}

@Composable
private fun LoginHeader(
    onLanguageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.login_logo_homepage),
            contentDescription = stringResource(R.string.login_logo_desc),
            modifier = Modifier.size(width = 48.dp, height = 44.dp),
        )
        LanguageSelector()
    }
}

@Composable
private fun GoogleLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .width(246.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(LoginButtonBackground)
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.login_login_google),
            color = LoginButtonText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.login_ic_google),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun LoginScreenPreview() {
    SAATheme {
        LoginScreen()
    }
}
