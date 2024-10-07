package com.example.todojpc.pages


import androidx.room.TypeConverter
import java.util.Date

/*
class ConvertersClass {

    @TypeConverter
    fun fromData(date : Date) : Long{
        return date.time
    }

    @TypeConverter
    fun toDate(time : Long) : Date{
        return Date(time)
    }
}*/

class ConvertersClass {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(time: Long?): Date? {
        return time?.let { Date(it) }
    }
}

