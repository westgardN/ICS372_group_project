package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import android.arch.persistence.room.TypeConverter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Converter {

    @TypeConverter
    public static LocalDate toLocalDate(Long timestamp) {
        return timestamp == null ? null : LocalDate.ofEpochDay(timestamp / 86400L);
    }

    @TypeConverter
    public static Long toTimestamp(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    @TypeConverter
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        return timestamp == null ? null : LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofTotalSeconds(0));
    }

    @TypeConverter
    public static Long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime.toString()).getTime();
    }

    @TypeConverter
    public static String valueToString(Object value) {
        return value == null ? null : value.toString();
    }

}
