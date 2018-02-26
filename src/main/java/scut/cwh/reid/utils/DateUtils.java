package scut.cwh.reid.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date addSecond(Date oldDate, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(oldDate);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }
}
