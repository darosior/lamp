package com.lvaccaro.lamp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

class UI {

    companion object {
        fun copyToClipboard(context: Context, key: String, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(key, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

}