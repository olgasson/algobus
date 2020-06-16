package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static Date date = new Date();
    private static DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");


    /**  2020-01-01 10:45:23 */
    private static DateFormat bitfinexFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");


    public static String getFormattedTimestamp(long timestamp) {
        date.setTime(timestamp);
        return formatter.format(date);
    }

}
