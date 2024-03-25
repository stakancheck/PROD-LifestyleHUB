/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


fun actionView(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(context, intent, null)
}

fun actionPhoneNumber(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse(url)
    }
    startActivity(context, intent, null)
}
