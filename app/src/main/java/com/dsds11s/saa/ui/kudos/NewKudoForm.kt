package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.R
import com.dsds11s.saa.ui.theme.Montserrat

// Figma tokens — screen PV7jBVZU1N, node 6885:9903
private val CardBg = Color(0xFFFFF8E1) // rgba(255,248,225,1)
private val CardRadius = 10.7.dp // 10.723404px
private val TitleColor = Color(0xFF00101A) // rgba(0,16,26,1)
private val HelperTextColor = Color(0xFF999999) // rgba(153,153,153,1)
private val ErrorColor = Color(0xFFD4271D) // rgba(212,39,29,1)

/**
 * Cream form card containing all kudo composition fields.
 * Figma node 6885:9903 — bg #FFF8E1, radius 10.7dp, padding 18dp v / 12dp h.
 */
@Composable
fun NewKudoForm(
    state: NewKudoFormState,
    onRecipientFieldClick: () -> Unit,
    onRecipientDismiss: () -> Unit,
    onRecipientSelect: (KudoRecipient) -> Unit,
    onAwardTitleChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onAddHashtag: () -> Unit,
    onHashtagDismiss: () -> Unit,
    onRemoveHashtag: (String) -> Unit,
    onToggleHashtag: (String) -> Unit,
    onRemoveImage: (android.net.Uri) -> Unit,
    onAddImage: () -> Unit,
    onAnonymousToggle: () -> Unit,
    onCommunityStandards: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CardRadius))
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Card title — Figma 6885:9904: bold, 14sp
        Text(
            text = stringResource(R.string.newkudo_card_title),
            color = TitleColor,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
        )

        // Recipient field with dropdown
        NewKudoRecipientField(
            label = stringResource(R.string.newkudo_recipient_label),
            selected = state.recipient,
            query = state.recipientQuery,
            dropdownOpen = state.showRecipientDropdown,
            searchResults = if (state.showRecipientDropdown) mockRecipients else emptyList(),
            onFieldClick = onRecipientFieldClick,
            onDismiss = onRecipientDismiss,
            onSelect = onRecipientSelect,
        )

        // Award title — "Danh hiệu"
        LabeledInputField(
            label = stringResource(R.string.newkudo_award_label),
            isRequired = true,
            value = state.awardTitle,
            placeholder = stringResource(R.string.newkudo_award_placeholder),
            onValueChange = onAwardTitleChange,
        )

        Text(
            text = stringResource(R.string.newkudo_award_helper),
            color = HelperTextColor,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
        )

        // Message editor (toolbar + text area) + mention helper
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            NewKudoMessageEditor(
                value = state.message,
                onValueChange = onMessageChange,
                onCommunityStandards = onCommunityStandards,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.newkudo_message_mention_helper),
                color = HelperTextColor,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
        }

        // Hashtag field
        NewKudoHashtagField(
            selected = state.selectedHashtags,
            dropdownOpen = state.showHashtagDropdown,
            onAddClick = onAddHashtag,
            onDismiss = onHashtagDismiss,
            onRemove = onRemoveHashtag,
            onToggle = onToggleHashtag,
        )

        // Image row
        NewKudoImageRow(
            images = state.images,
            onRemove = onRemoveImage,
            onAddImage = onAddImage,
        )

        // Anonymous toggle
        AnonymousToggleRow(
            checked = state.isAnonymous,
            onToggle = onAnonymousToggle,
        )

        // Validation error message — all 4 required fields (spec 7fFAb-K35a)
        if (state.showValidationError) {
            Text(
                text = stringResource(R.string.newkudo_validation_error),
                color = ErrorColor,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}
