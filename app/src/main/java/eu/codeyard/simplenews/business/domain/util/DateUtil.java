package eu.codeyard.simplenews.business.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Long convertTimeToMilliSec(String time) {
        if (time != null && !time.isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                        .parse(time);

                if (date != null) {
                    return date.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return 0L;
    }

    public static String convertMilliSecToTime(Long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        return dateFormat.format(calendar.getTime());
    }
}
