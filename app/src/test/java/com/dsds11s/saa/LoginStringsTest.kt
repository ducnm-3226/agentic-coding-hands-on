package com.dsds11s.saa

import com.dsds11s.saa.ui.login.Language
import com.dsds11s.saa.ui.login.loginStringsFor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Language switch re-renders description + copyright (TC_LOGIN_FUN_004). */
class LoginStringsTest {
    @Test
    fun everyLanguageHasNonBlankCopy() {
        Language.entries.forEach { lang ->
            val strings = loginStringsFor(lang)
            assertTrue("description blank for $lang", strings.description.isNotBlank())
            assertTrue("copyright blank for $lang", strings.copyright.isNotBlank())
        }
    }

    @Test
    fun englishCopy_matchesDesign() {
        val en = loginStringsFor(Language.ENGLISH)
        assertEquals("Start your journey with SAA 2025.\nLog in to explore!", en.description)
        assertEquals("Copyright belongs to Sun* © 2025", en.copyright)
    }

    @Test
    fun languages_haveDistinctDescriptions() {
        val descriptions = Language.entries.map { loginStringsFor(it).description }.toSet()
        assertEquals(Language.entries.size, descriptions.size)
    }
}
