package com.dsds11s.saa.ui.kudos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Loads a downsampled thumbnail for a content [uri] off the main thread and returns it as an
 * [ImageBitmap]. Returns null until decoded (or if decoding fails). Avoids an image library by
 * decoding with BitmapFactory + inSampleSize — sufficient for small thumbnails on API 24+.
 */
@Composable
internal fun rememberImageThumbnail(
    uri: Uri,
    targetPx: Int = 144,
): ImageBitmap? {
    val context = LocalContext.current
    var bitmap by remember(uri, targetPx) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(uri, targetPx) {
        bitmap =
            withContext(Dispatchers.IO) {
                decodeDownsampled(context, uri, targetPx)?.asImageBitmap()
            }
    }
    return bitmap
}

/** Decode [uri] to a Bitmap downsampled so its largest side is ~[targetPx]. Null on failure. */
private fun decodeDownsampled(
    context: Context,
    uri: Uri,
    targetPx: Int,
): Bitmap? {
    return try {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, bounds)
        }
        val largest = maxOf(bounds.outWidth, bounds.outHeight)
        if (largest <= 0) return null

        var sample = 1
        while (largest / sample > targetPx) sample *= 2

        val opts = BitmapFactory.Options().apply { inSampleSize = sample }
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, opts)
        }
    } catch (e: Exception) {
        null
    }
}
