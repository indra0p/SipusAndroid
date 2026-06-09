package com.sipus.core.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Double.toRupiah(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(this).replace(",00", "")
}

fun Int.toRupiah(): String = this.toDouble().toRupiah()

fun String?.formatDate(from: String = "yyyy-MM-dd", to: String = "dd MMM yyyy"): String {
    if (this.isNullOrBlank()) return "-"
    return try {
        val sdf = SimpleDateFormat(from, Locale("id", "ID"))
        val date = sdf.parse(this) ?: return this
        SimpleDateFormat(to, Locale("id", "ID")).format(date)
    } catch (_: Exception) { this }
}

fun String?.formatDateTime(): String = formatDate("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy, HH:mm")
