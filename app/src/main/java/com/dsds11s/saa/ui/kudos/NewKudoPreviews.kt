package com.dsds11s.saa.ui.kudos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dsds11s.saa.ui.theme.SAATheme

/** Preview 1: Base form — empty default state ("Viết Kudo_default"), 5 image thumbs. */
@Preview(
    showBackground = true,
    backgroundColor = 0xFF00101A,
    widthDp = 375,
    heightDp = 812,
    name = "NewKudo — Base form",
)
@Composable
private fun NewKudoBasePreview() {
    SAATheme {
        NewKudoScreen()
    }
}

/** Preview 2: Validation error — required fields empty, red error text shown. */
@Preview(
    showBackground = true,
    backgroundColor = 0xFF00101A,
    widthDp = 375,
    heightDp = 812,
    name = "NewKudo — Validation error",
)
@Composable
private fun NewKudoValidationErrorPreview() {
    SAATheme {
        NewKudoFormPreviewWrapper(
            state =
                NewKudoFormState(
                    recipient = null,
                    message = "",
                    selectedHashtags = emptyList(),
                    showValidationError = true,
                ),
        )
    }
}

/** Preview 3: Recipient dropdown open. */
@Preview(
    showBackground = true,
    backgroundColor = 0xFF00101A,
    widthDp = 375,
    heightDp = 812,
    name = "NewKudo — Recipient dropdown open",
)
@Composable
private fun NewKudoRecipientDropdownPreview() {
    SAATheme {
        NewKudoFormPreviewWrapper(
            state =
                NewKudoFormState(
                    showRecipientDropdown = true,
                    showHashtagDropdown = false,
                ),
        )
    }
}

/** Preview 4: Hashtag dropdown open. */
@Preview(
    showBackground = true,
    backgroundColor = 0xFF00101A,
    widthDp = 375,
    heightDp = 812,
    name = "NewKudo — Hashtag dropdown open",
)
@Composable
private fun NewKudoHashtagDropdownPreview() {
    SAATheme {
        NewKudoFormPreviewWrapper(
            state =
                NewKudoFormState(
                    showRecipientDropdown = false,
                    showHashtagDropdown = true,
                    selectedHashtags = listOf("BE OPTIMISTIC", "WASSHOI", "BE A TEAM"),
                ),
        )
    }
}

/**
 * Thin preview wrapper that passes a fixed state to the form for visual snapshots.
 * Uses a stateless form rendering via NewKudoForm directly.
 */
@Composable
private fun NewKudoFormPreviewWrapper(state: NewKudoFormState) {
    NewKudoForm(
        state = state,
        onRecipientFieldClick = {},
        onRecipientDismiss = {},
        onRecipientSelect = {},
        onAwardTitleChange = {},
        onMessageChange = {},
        onAddHashtag = {},
        onHashtagDismiss = {},
        onRemoveHashtag = {},
        onToggleHashtag = {},
        onRemoveImage = {},
        onAddImage = {},
        onAnonymousToggle = {},
        onCommunityStandards = {},
    )
}
