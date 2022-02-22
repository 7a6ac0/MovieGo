package tabacowang.me.moviego.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class CalendarDeserializer : JsonDeserializer<Calendar> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar? {
        return try {
            json?.asString?.toCalendarOrNull()
        } catch (e: Exception) {
            try {
                val parsedResult = SimpleDateFormat.getDateTimeInstance().parse(json?.asString ?: "")
                Calendar.getInstance().apply {
                    time = parsedResult!!
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}