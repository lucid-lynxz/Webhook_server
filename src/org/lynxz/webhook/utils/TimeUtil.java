package org.lynxz.webhook.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zxz on 2016/7/7.
 * 日期和好毫秒的转换
 */
public class TimeUtil {
    public static DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String MIN = "min";
    public static final String HOUR = "hour";
    public static final String DAY = "day";
    public static final String WEEK = "week";
    private static final Date date = new Date();

    /**
     * 时间毫秒转日期字符串 "yyyy-MM-dd hh:mm:ss"
     *
     * @param msec 毫秒值
     * @return
     */
    public static String msec2date(long msec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(msec);
        return mFormatter.format(calendar.getTime());
    }

    public static long klineType2seconds(String klineType) {
        long timeNum;
        if (klineType.contains(MIN)) {
            timeNum = Integer.valueOf(klineType.replaceAll(MIN, ""));
            return timeNum * 60;
        } else if (klineType.contains(HOUR)) {
            timeNum = Integer.valueOf(klineType.replaceAll(HOUR, ""));
            return klineType2seconds((timeNum * 60) + MIN);
        } else if (klineType.contains(DAY)) {
            timeNum = Integer.valueOf(klineType.replaceAll(DAY, ""));
            return klineType2seconds((timeNum * 24) + HOUR);
        } else if (klineType.contains(WEEK)) {
            timeNum = Integer.valueOf(klineType.replaceAll(WEEK, ""));
            return klineType2seconds((timeNum * 7) + DAY);
        }
        return 0;
    }

    public static Timestamp msec2TimeStamp(long msec) {
        date.setTime(msec);
        return Timestamp.valueOf(mFormatter.format(date));
    }
}
