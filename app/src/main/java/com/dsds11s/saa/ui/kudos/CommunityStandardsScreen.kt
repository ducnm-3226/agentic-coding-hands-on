package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.theme.Montserrat

/**
 * CommunityStandardsScreen — Figma [iOS] Sun*Kudos_Tiêu chuẩn cộng đồng (6885:10806).
 *
 * Static informational page: ROOT FURTHER banner, Community Standards (10 criteria),
 * and Security Standards. Reached from the "Tiêu chuẩn cộng đồng" link in the New Kudo editor.
 * Tokens, content list and list-item primitives live in CommunityStandardsComponents.kt.
 *
 * @param onBack back chevron tap (returns to the New Kudo form)
 */
@Composable
fun CommunityStandardsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(CsScreenBg),
    ) {
        HeroKeyvisualBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(520.dp)
                    .align(Alignment.TopStart),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(56.dp)) // clear the sticky top bar (matches its height)

            // A — ROOT FURTHER logo banner (reuse Login asset).
            Image(
                painter = painterResource(R.drawable.login_logo_rootfuther),
                contentDescription = "ROOT FURTHER",
                modifier = Modifier.size(width = 200.dp, height = 88.dp),
            )

            // B — Community Standards section.
            SectionHeading(stringResource(R.string.kudos_cs_community_heading))
            Text(
                text = stringResource(R.string.kudos_cs_community_intro),
                color = CsGold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.kudos_cs_community_warning),
                color = CsMuted,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
            val localCriteria =
                listOf(
                    stringResource(R.string.kudos_cs_criterion_1),
                    stringResource(R.string.kudos_cs_criterion_2),
                    stringResource(R.string.kudos_cs_criterion_3),
                    stringResource(R.string.kudos_cs_criterion_4),
                    stringResource(R.string.kudos_cs_criterion_5),
                    stringResource(R.string.kudos_cs_criterion_6),
                    stringResource(R.string.kudos_cs_criterion_7),
                    stringResource(R.string.kudos_cs_criterion_8),
                    stringResource(R.string.kudos_cs_criterion_9),
                    stringResource(R.string.kudos_cs_criterion_10),
                )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                localCriteria.forEachIndexed { index, text ->
                    NumberedItem(number = index + 1, text = text)
                }
            }

            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CsDivider))

            // C — Security Standards section.
            SectionHeading(stringResource(R.string.kudos_cs_security_heading))
            Text(
                text = stringResource(R.string.kudos_cs_security_intro),
                color = CsBody,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
            BulletItem(stringResource(R.string.kudos_cs_security_bullet_1))
            BulletItem(stringResource(R.string.kudos_cs_security_bullet_2))
            Text(
                text = stringResource(R.string.kudos_cs_security_contact),
                color = CsGold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
            )
        }

        // Sticky top bar. Title "Tiêu chuẩn chung" (General Standards) is intentional per the
        // Figma — the page hosts BOTH the community and security standard sections.
        KudosTopBar(
            title = stringResource(R.string.kudos_community_standards_screen_title),
            onBack = onBack,
            modifier = Modifier.align(Alignment.TopStart),
        )
    }
}
