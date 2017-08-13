package org.codedoesgood.mercury.MainActivityTests.Tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.codedoesgood.mercury.view.MainActivity;
import org.codedoesgood.mercury.MainActivityTests.Robots.MainActivityRobot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void isDefaultLayoutLoaded(){
        MainActivityRobot activityRobot = new MainActivityRobot();
        activityRobot.verifyTextIsVisible("Hello World!");
    }
}
