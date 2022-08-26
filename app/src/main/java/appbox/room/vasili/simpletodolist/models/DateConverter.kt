package appbox.room.vasili.simpletodolist.models

import androidx.room.TypeConverter
import java.util.*


object DateConverter {
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
