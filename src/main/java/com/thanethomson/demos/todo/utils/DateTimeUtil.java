package com.thanethomson.demos.todo.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateTimeUtil {

    public static final String DEFAULT_TIMEZONE = "Africa/Johannesburg";
    public static final SimpleDateFormat ISO8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    static {
        ISO8601_FORMAT.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
    }

}
