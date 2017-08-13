package org.codedoesgood.mercury.utilities;

import android.content.Context;
import android.text.format.DateFormat;

import org.codedoesgood.mercury.R;

import java.util.Calendar;

/**
 *  Utility class for formatting and converting date and time values
 */
public final class DateTimeFormatUtil {

    private static final int FORMAT_TYPE_OTHER = 0;
    private static final int FORMAT_TYPE_TODAY = 2;
    private static final int FORMAT_TYPE_TOMORROW = 3;
    private static final int FORMAT_TYPE_YESTERDAY = 1;

    private DateTimeFormatUtil() { }

    /**
     * Use to convert a date in milliseconds to a String based on the device's locale.
     * @param context Application context; used for retrieving the default locale.
     * @param dateInMillis The date to be converted from milliseconds
     * @return A string representing the date that was passed in formatted for the user's locale.
     */
    public static String dateFormatedForLocale(Context context, long dateInMillis) {
        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(context);

        int formatType = getFormatType(dateInMillis);
        if (formatType == FORMAT_TYPE_TODAY) {
            String dateFormatted = context.getResources().getString(R.string.date_format_util_today,
                    timeFormat.format(dateInMillis));
            return dateFormatted;
        } else if (formatType == FORMAT_TYPE_TOMORROW) {
            String dateFormatted = context.getResources().getString(R.string.date_format_util_tomorrow,
                    timeFormat.format(dateInMillis));
            return dateFormatted;
        } else if (formatType == FORMAT_TYPE_YESTERDAY) {
            String dateFormatted = context.getResources().getString(R.string.date_format_util_yesterday,
                    timeFormat.format(dateInMillis));
            return dateFormatted;
        } else {
            java.text.DateFormat dateFormat = DateFormat.getLongDateFormat(context);
            return dateFormat.format(dateInMillis);
        }
    }

    /**
     *
     * @param dateInMillis The millisecond value representing the date to check
     * @return True if the date is yesterday's date, else false.
     */
    public static int getFormatType(long dateInMillis) {
        Calendar timeToCheck = Calendar.getInstance();
        timeToCheck.setTimeInMillis(dateInMillis);

        int checkYear = timeToCheck.get(Calendar.YEAR);
        int checkDay = timeToCheck.get(Calendar.DAY_OF_YEAR);

        timeToCheck.setTimeInMillis(System.currentTimeMillis());
        int currentDayNumber = timeToCheck.get(Calendar.DAY_OF_YEAR);
        int tomorrow = currentDayNumber + 1;
        int yesterday = currentDayNumber - 1;


        if (timeToCheck.get(Calendar.YEAR) != checkYear) {
            return FORMAT_TYPE_OTHER;
        }
        if (checkDay == tomorrow) { return FORMAT_TYPE_TOMORROW; }
        if (checkDay == currentDayNumber) { return FORMAT_TYPE_TODAY; }
        if (checkDay == yesterday) { return FORMAT_TYPE_YESTERDAY; }
        return FORMAT_TYPE_OTHER;
    }
}
