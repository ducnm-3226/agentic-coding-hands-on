package com.dsds11s.saa

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Guard against a runtime-only crash class: an XML `<bitmap>` drawable.
 *
 * Compose `painterResource()` parses EVERY `.xml` drawable as a VectorDrawable, so a
 * `<bitmap>` root throws at inflation time — yet aapt2/assembleDebug accept it, so the
 * build stays green and the crash only surfaces when the screen composes. This test
 * fails the build instead, the moment such a file is added.
 *
 * (Regression: the Awards badge placeholders were `<bitmap>` aliases and crashed the
 * Awards screen on navigation.)
 */
class DrawableResourceTest {
    @Test
    fun noBitmapXmlDrawables() {
        val resDir =
            listOf(File("src/main/res"), File("app/src/main/res"))
                .firstOrNull { it.exists() }
                ?: error("res dir not found from ${File("").absolutePath}")

        val offenders =
            resDir.walkTopDown()
                .filter { it.isFile && it.extension == "xml" && it.parentFile.name.startsWith("drawable") }
                .filter { it.readText().contains(Regex("""<\s*bitmap\b""")) }
                .map { it.name }
                .toList()

        assertTrue(
            "XML <bitmap> drawables crash Compose painterResource() (it parses .xml as a " +
                "VectorDrawable). Use a raster asset (png/webp) instead. Offenders: $offenders",
            offenders.isEmpty(),
        )
    }
}
