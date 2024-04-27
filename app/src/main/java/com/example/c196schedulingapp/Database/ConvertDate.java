package com.example.c196schedulingapp.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class ConvertDate {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }
        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
}
