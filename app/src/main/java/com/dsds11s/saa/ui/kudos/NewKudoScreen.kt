package com.dsds11s.saa.ui.kudos

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dsds11s.saa.ui.home.HeroKeyvisualBackground
import com.dsds11s.saa.ui.home.HomeBottomNav

// Screen-scoped design tokens — Figma [iOS] Sun*Kudos Gửi lời chúc Kudos (PV7jBVZU1N)
private val ScreenBg = Color(0xFF00101A) // dark bg #00101A

/**
 * New Kudo composition screen — Figma [iOS] Sun*Kudos_Gửi lời chúc Kudos (node 6885:9883).
 *
 * Renders 4 states from local UI state:
 *  1. Base form (default)
 *  2. Recipient dropdown open
 *  3. Hashtag dropdown open
 *  4. Validation error (after tapping "Gửi đi" with missing required fields)
 *
 * Presentational composable: no ViewModel, no repository.
 *
 * @param onCancel       called when "Huỷ" button is tapped
 * @param onSend         called only after validation passes (recipient + title + message + ≥1 hashtag)
 * @param onTabSelected  bottom nav selection (0=Home, 1=Awards, 2=Kudos, 3=Profile)
 */
@Composable
fun NewKudoScreen(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = {},
    onSend: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onCommunityStandards: () -> Unit = {},
) {
    // `remember` (not rememberSaveable): NewKudoFormState is a non-Parcelable data class,
    // so the saveable registry would crash on rotation. Form resets on config change —
    // acceptable for this presentational mock; a Saver/@Parcelize is the future upgrade.
    // Starts as the empty "Viết Kudo_default" form (spec 7fFAb-K35a).
    var state by remember { mutableStateOf(NewKudoFormState()) }

    // Device photo picker (spec F.5) — Jetpack Photo Picker, no runtime permission needed.
    // On legacy devices without the system picker this falls back to ACTION_OPEN_DOCUMENT,
    // which does not enforce maxItems in its UI — appendCapped (distinct + take 5) is the cap.
    val pickImages =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(KUDO_IMAGE_MAX),
        ) { uris ->
            if (uris.isNotEmpty()) {
                state = state.copy(images = appendCapped(state.images, uris, KUDO_IMAGE_MAX))
            }
        }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(ScreenBg),
    ) {
        // Decorative keyvisual hero band — top area (same as KudosScreen)
        HeroKeyvisualBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.TopStart),
        )

        // Scrollable content
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp),
        ) {
            NewKudoTopBar(onBack = onCancel)

            Spacer(modifier = Modifier.height(16.dp))

            NewKudoForm(
                state = state,
                onRecipientFieldClick = {
                    state =
                        state.copy(
                            showRecipientDropdown = true,
                            showHashtagDropdown = false,
                        )
                },
                onRecipientDismiss = { state = state.copy(showRecipientDropdown = false) },
                onRecipientSelect = { recipient ->
                    state =
                        state.copy(
                            recipient = recipient,
                            recipientQuery = recipient.name,
                            showRecipientDropdown = false,
                            showValidationError = false,
                        )
                },
                onAwardTitleChange = {
                    state = state.copy(awardTitle = it.take(KUDO_TITLE_MAX), showValidationError = false)
                },
                onMessageChange = {
                    state = state.copy(message = it.take(KUDO_MESSAGE_MAX), showValidationError = false)
                },
                onAddHashtag = {
                    state =
                        state.copy(
                            showHashtagDropdown = true,
                            showRecipientDropdown = false,
                        )
                },
                onHashtagDismiss = { state = state.copy(showHashtagDropdown = false) },
                onRemoveHashtag = { tag ->
                    state = state.copy(selectedHashtags = state.selectedHashtags - tag)
                },
                onToggleHashtag = { tag ->
                    val current = state.selectedHashtags
                    state =
                        if (current.contains(tag)) {
                            state.copy(selectedHashtags = current - tag, showValidationError = false)
                        } else if (current.size < 5) {
                            state.copy(selectedHashtags = current + tag, showValidationError = false)
                        } else {
                            state
                        }
                },
                onRemoveImage = { uri ->
                    state = state.copy(images = state.images.filter { it != uri })
                },
                onAddImage = {
                    pickImages.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
                onAnonymousToggle = {
                    state = state.copy(isAnonymous = !state.isAnonymous)
                },
                onCommunityStandards = onCommunityStandards,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            NewKudoActionBar(
                onCancel = onCancel,
                onSend = {
                    val valid =
                        isNewKudoFormValid(
                            state.recipient,
                            state.awardTitle,
                            state.message,
                            state.selectedHashtags,
                        )
                    if (valid) {
                        state = state.copy(showValidationError = false)
                        onSend()
                    } else {
                        state = state.copy(showValidationError = true)
                    }
                },
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Sticky bottom nav
        HomeBottomNav(
            selectedIndex = 2,
            onTabSelected = onTabSelected,
            modifier =
                Modifier
                    .align(Alignment.BottomStart)
                    .navigationBarsPadding(),
        )
    }
}
