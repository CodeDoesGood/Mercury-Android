package org.codedoesgood.mercury.utilitytests.Tests;

import android.content.Context;

import org.codedoesgood.mercury.BuildConfig;

import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.utilitytests.Robots.DateTimeFormatRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Calendar;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DateTimeFormatTest {

    private Context context;
    private DateTimeFormatRobot robot;

    @Before
    public void setup() {
        context = RuntimeEnvironment.application;
        robot = new DateTimeFormatRobot();
    }

    @Test
    public void pastDateFormat(){
        String pastYear = "2014";
        assertTrue(robot.verifyDateFormat(context, 1395028800000L, pastYear));
    }

    @Test
    public void futureDateFormat(){
        String futureYear = "2029";
        assertTrue(robot.verifyDateFormat(context, 1868414400000L, futureYear));
    }

    @Test
    public void yesterdayFormat(){
        String expectedValue = getTestCaseExpectedValue(R.string.date_format_util_yesterday);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        assertTrue(robot.verifyDateFormat(context, cal.getTimeInMillis(), expectedValue));
    }

    @Test
    public void todayFormat(){
        String expectedValue = getTestCaseExpectedValue(R.string.date_format_util_today);
        Calendar cal = Calendar.getInstance();
        assertTrue(robot.verifyDateFormat(context, cal.getTimeInMillis(), expectedValue));
    }

    @Test
    public void tomorrowFormat(){
        String expectedValue = getTestCaseExpectedValue(R.string.date_format_util_tomorrow);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        assertTrue(robot.verifyDateFormat(context, cal.getTimeInMillis(), expectedValue));
    }

    private String getTestCaseExpectedValue(int resourceID){
        String expectedValue = context.getString(resourceID);
        String[] valueArray = expectedValue.split(" ");
        return valueArray[0];
    }

}
