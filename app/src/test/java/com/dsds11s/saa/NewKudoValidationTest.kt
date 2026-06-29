package com.dsds11s.saa

import com.dsds11s.saa.ui.kudos.KudoRecipient
import com.dsds11s.saa.ui.kudos.appendCapped
import com.dsds11s.saa.ui.kudos.isNewKudoFormValid
import com.dsds11s.saa.ui.kudos.stripNumberedList
import com.dsds11s.saa.ui.kudos.stripQuotePrefix
import com.dsds11s.saa.ui.kudos.toNumberedList
import com.dsds11s.saa.ui.kudos.toQuotePrefix
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for New Kudo form validation logic (spec 7fFAb-K35a, send rule I).
 *
 * Validates the four required rules:
 * 1. Recipient must be non-null
 * 2. Title (Danh hiệu) must be non-blank
 * 3. Message must be non-blank
 * 4. At least 1 hashtag must be selected
 */
class NewKudoValidationTest {
    private val mockRecipient = KudoRecipient("1", "Dương Huỳnh Xuân Nhật", "CECV1")
    private val title = "Người truyền động lực"
    private val message = "Tôi rất chi là quý bạn"
    private val tags = listOf("BE OPTIMISTIC")

    @Test
    fun validFormWithAllFieldsSet() {
        assertTrue(
            "Form with recipient, title, message, and hashtags should pass",
            isNewKudoFormValid(mockRecipient, title, message, tags),
        )
    }

    // ── Recipient ──────────────────────────────────────────────────────────────

    @Test
    fun invalidFormWithNullRecipient() {
        assertFalse(
            "Null recipient should fail",
            isNewKudoFormValid(null, title, message, tags),
        )
    }

    // ── Title (Danh hiệu) ────────────────────────────────────────────────────────

    @Test
    fun invalidFormWithEmptyTitle() {
        assertFalse(
            "Empty title should fail (title is required)",
            isNewKudoFormValid(mockRecipient, "", message, tags),
        )
    }

    @Test
    fun invalidFormWithBlankTitle() {
        assertFalse(
            "Whitespace-only title should fail",
            isNewKudoFormValid(mockRecipient, "   ", message, tags),
        )
    }

    @Test
    fun validFormWithMaxLengthTitle() {
        assertTrue(
            "100-char title should pass",
            isNewKudoFormValid(mockRecipient, "A".repeat(100), message, tags),
        )
    }

    // ── Message ──────────────────────────────────────────────────────────────

    @Test
    fun invalidFormWithEmptyMessage() {
        assertFalse(
            "Empty message should fail",
            isNewKudoFormValid(mockRecipient, title, "", tags),
        )
    }

    @Test
    fun invalidFormWithBlankMessage() {
        assertFalse(
            "Whitespace-only message should fail",
            isNewKudoFormValid(mockRecipient, title, "   ", tags),
        )
    }

    @Test
    fun validFormWithMinimalMessage() {
        assertTrue(
            "Single-character message should pass",
            isNewKudoFormValid(mockRecipient, title, "A", tags),
        )
    }

    // ── Hashtags ──────────────────────────────────────────────────────────────

    @Test
    fun invalidFormWithNoHashtags() {
        assertFalse(
            "Empty hashtag list should fail",
            isNewKudoFormValid(mockRecipient, title, message, emptyList()),
        )
    }

    @Test
    fun validFormWithMaxHashtags() {
        val five = listOf("BE OPTIMISTIC", "WASSHOI", "BE A TEAM", "GET RISKY", "GO FAST")
        assertTrue(
            "5 hashtags (UI max) should pass",
            isNewKudoFormValid(mockRecipient, title, message, five),
        )
    }

    @Test
    fun validatorDoesNotEnforceMaxHashtags() {
        // The ≤5 cap is a UI constraint; the validator only checks for ≥1.
        val six = listOf("a", "b", "c", "d", "e", "f")
        assertTrue(
            "Validator is permissive about list size (UI enforces max 5)",
            isNewKudoFormValid(mockRecipient, title, message, six),
        )
    }

    // ── Combined ──────────────────────────────────────────────────────────────

    @Test
    fun invalidFormWithAllFieldsMissing() {
        assertFalse(
            "All required fields missing should fail",
            isNewKudoFormValid(null, "", "", emptyList()),
        )
    }

    @Test
    fun invalidFormMissingOnlyTitle() {
        assertFalse(
            "Form missing only the title should fail",
            isNewKudoFormValid(mockRecipient, "", message, tags),
        )
    }

    // ── Image attachment cap (spec F, max 5) ─────────────────────────────────────

    @Test
    fun appendCappedKeepsAllWhenUnderLimit() {
        assertEquals(
            listOf("a", "b", "c"),
            appendCapped(listOf("a"), listOf("b", "c"), 5),
        )
    }

    @Test
    fun appendCappedTruncatesAtMax() {
        val result = appendCapped(listOf("a", "b", "c"), listOf("d", "e", "f", "g"), 5)
        assertEquals("Should cap at 5 items", 5, result.size)
        assertEquals(listOf("a", "b", "c", "d", "e"), result)
    }

    @Test
    fun appendCappedNoChangeWhenAlreadyFull() {
        val full = listOf("a", "b", "c", "d", "e")
        assertEquals(full, appendCapped(full, listOf("f"), 5))
    }

    @Test
    fun appendCappedDeduplicatesAcrossSessions() {
        // Picking the same item again (e.g. reopening the picker) must not add a duplicate.
        assertEquals(
            listOf("a", "b", "c"),
            appendCapped(listOf("a", "b"), listOf("b", "c"), 5),
        )
    }

    // ── Message formatting transforms (toolbar numbered-list / quote) ────────────

    @Test
    fun numberedListPrefixesEachLineAndRoundTrips() {
        val input = "first\nsecond\nthird"
        val numbered = toNumberedList(input)
        assertEquals("1. first\n2. second\n3. third", numbered)
        assertEquals("Stripping restores the original", input, stripNumberedList(numbered))
    }

    @Test
    fun numberedListSkipsBlankLinesAndIsIdempotent() {
        val input = "a\n\nb"
        assertEquals("1. a\n\n2. b", toNumberedList(input))
        // Re-applying does not stack prefixes.
        assertEquals("1. a\n\n2. b", toNumberedList(toNumberedList(input)))
    }

    @Test
    fun quotePrefixesEachLineAndRoundTrips() {
        val input = "hello\nworld"
        val quoted = toQuotePrefix(input)
        assertEquals("> hello\n> world", quoted)
        assertEquals(input, stripQuotePrefix(quoted))
    }

    @Test
    fun transformsOnEmptyTextAreNoOps() {
        assertEquals("", toNumberedList(""))
        assertEquals("", toQuotePrefix(""))
    }
}
