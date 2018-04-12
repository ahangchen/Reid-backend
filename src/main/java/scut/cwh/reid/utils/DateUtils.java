package scut.cwh.reid.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date addSecond(Date oldDate, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(oldDate);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }

    public static boolean dateSameUntilSecond(Date a,Date b){
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String aTime = dateFm.format(a);
        String bTime = dateFm.format(b);
        return aTime.equals(bTime);
    }

    public static String formatDate(Date date){
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFm.format(date);
    }
}
