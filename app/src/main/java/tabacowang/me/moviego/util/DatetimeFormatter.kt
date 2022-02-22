package tabacowang.me.moviego.util

import tabacowang.me.moviego.util.Formatter.DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
}

fun String.toCalendarOrNull(format: String = DATE_FORMAT): Calendar? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    val date = try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
    return date?.let { Calendar.getInstance().apply { time = it } }
}

fun Calendar.format(format: String = DATE_FORMAT): String? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this.time)
}