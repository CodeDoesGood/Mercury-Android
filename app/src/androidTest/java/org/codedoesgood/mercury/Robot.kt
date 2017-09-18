package org.codedoesgood.mercury

import android.app.Activity
import android.app.Instrumentation
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import org.hamcrest.Matchers.not
import timber.log.Timber
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.Espresso.onView

import android.support.test.rule.ActivityTestRule
import org.hamcrest.Matchers.containsString
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.support.test.InstrumentationRegistry


/**
 * Base robot class to mimic common high-level actions that a standard QA team member
 * would perform. Should only contain logic for how an action would be taken,
 * but not any specific values.
 */
open class Robot {

    val DIRECTION_UP = 0
    val DIRECTION_RIGHT = 1
    val DIRECTION_DOWN = 2
    val DIRECTION_LEFT = 3

    /**
     * Verify a button is on screen with the text [textToVerify]
     */
    fun checkViewWithTextVisible(textToVerify: String): Robot {
        onView(withText(textToVerify)).check(matches(isDisplayed()))
        return this
    }

    /**
     * Verify there is no button on the screen with the specified text.
     */
    fun checkViewWithTextNotVisible(textToVerify: String): Robot {
        onView(withText(textToVerify)).check(matches(not(isDisplayed())))
        return this
    }

    /**
     * Verify there is no view on the screen with the specified view ID.
     */
    fun checkViewWithIdNotVisible(idToVerify: Int): Robot {
        onView(not(withId(idToVerify))).check(matches(not(isDisplayed())))
        return this
    }

    /**
     * Verify the view with the specified view ID is visible on the screen.
     */
    fun checkViewWithIdVisible(idToVerify: Int): Robot {
        onView(withId(idToVerify)).check(matches(isDisplayed()))
        return this
    }

    /**
     * Standard robot API to click a button
     *
     * @param buttonText Text of the button to click
     */
    fun clickButton(buttonText: String): Robot {
        onView(withText(buttonText)).perform(click())
        return this
    }

    /**
     * Standard robot API to click a button
     *
     * @param buttonText Text of the button to click
     */
    fun clickButton(buttonId: Int): Robot {
        onView(withId(buttonId)).perform(click())
        return this
    }

    /**
     * Standard robot API to swipe a given direction on the specified view
     *
     * @param direction Direction to swipe
     * @param view The view on which to swipe
     */
    fun swipeDirection(direction: Int, view: Int): Robot {
        when (direction) {
            DIRECTION_UP -> onView(withId(view)).perform(swipeUp())
            DIRECTION_RIGHT -> onView(withId(view)).perform(swipeRight())
            DIRECTION_DOWN -> onView(withId(view)).perform(swipeDown())
            DIRECTION_LEFT -> onView(withId(view)).perform(swipeLeft())
            else -> {
                Timber.v("Unknown swipe direction")
            }
        }
        return this
    }

    /**
     * Standard robot API to enter text in a field and automatically close the soft keyboard
     *
     * @param text Text to enter in the field.
     * @param view The view in which to enter the text
     */
    fun enterTextInField(text: String, view: Int): Robot {
        onView(withId(view)).perform(typeText(text))
        closeSoftKeyboard()
        return this
    }

    /**
     * Standard robot API to verify that a toast message has been displayed containing the specified text.
     *
     * @param text Text to validate in the Toast
     * @param activityTestRule [ActivityTestRule] - Used to obtain the activity window
     * @param timeOutSeconds Optional time to wait for the Toast to display. Default is 3 seconds.
     */
    fun checkForToastContainingText(text: String, activityTestRule: ActivityTestRule<*>, timeOutSeconds: Int = 3): Robot {
        var passed = false
        var secondsElapsed = 0
        val decorView = activityTestRule.activity.window.decorView

        //NoMatchingRootException
        while (!passed && secondsElapsed < timeOutSeconds + 1) {
            try {
                onView(withText(containsString(text)))
                        .inRoot(withDecorView(not(decorView)))
                        .check(matches(isDisplayed()))
                passed = true
            } catch (e: Exception) {
                if (secondsElapsed >= timeOutSeconds) {
                    throw Exception("Time-out exceeded before toast containing message '$text' was found")
                }
                Thread.sleep(1000L)
                secondsElapsed++
            }
        }
        return this
    }

    /**
     * Standard robot API to verify that a toast message has been displayed containing the specified text.
     *
     * @param text Text to validate in the Toast
     * @param activity [Activity] - Used to obtain the activity window
     * @param timeOutSeconds Optional time to wait for the Toast to display. Default is 3 seconds.
     */
    fun checkForToastContainingText(text: String, activity: Activity, timeOutSeconds: Int = 3): Robot {
        var passed = false
        var secondsElapsed = 0
        val decorView = activity.window.decorView

        //NoMatchingRootException
        while (!passed && secondsElapsed < timeOutSeconds + 1) {
            try {
                onView(withText(containsString(text)))
                        .inRoot(withDecorView(not(decorView)))
                        .check(matches(isDisplayed()))
                passed = true
            } catch (e: Exception) {
                if (secondsElapsed >= timeOutSeconds) {
                    throw Exception("Time-out exceeded before toast containing message '$text' was found")
                }
                Thread.sleep(1000L)
                secondsElapsed++
            }
        }
        return this
    }

    /**
     * Press the back button. If currently displayed activity is root activity,
     * pressing back button would result in application closing.
     */
    fun pressBackButton() {
        pressBack()
    }

    /**
     * Open the overflow icon and click an item in the menu
     * @param itemText The text of the menu item to select
     */
    fun clickOverflowMenuItem(itemText: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText(itemText)).perform(click())
    }
}
