package org.codedoesgood.mercury

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers.not
import timber.log.Timber

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
        onView(ViewMatchers.withText(textToVerify)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    /**
     * Verify there is no button on the screen with the specified text.
     */
    fun checkViewWithTextNotVisible(textToVerify: String): Robot {
        onView(ViewMatchers.withText(textToVerify)).check(matches(not(ViewMatchers.isDisplayed())))
        return this
    }

    /**
     * Verify there is no view on the screen with the specified view ID.
     */
    fun checkViewWithIdNotVisible(idToVerify: Int): Robot {
        onView(not(ViewMatchers.withId(idToVerify))).check(matches(not(ViewMatchers.isDisplayed())))
        return this
    }

    /**
     * Verify the view with the specified view ID is visible on the screen.
     */
    fun checkViewWithIdVisible(idToVerify: Int): Robot {
        onView(ViewMatchers.withId(idToVerify)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    /**
     * Standard robot API to click a button
     *
     * @param buttonText Text of the button to click
     */
    fun clickButton(buttonText: String): Robot {
        onView(ViewMatchers.withText(buttonText)).perform(ViewActions.click())
        return this
    }

    /**
     * Standard robot API to click a button
     *
     * @param buttonText Text of the button to click
     */
    fun clickButton(buttonId: Int): Robot {
        onView(ViewMatchers.withId(buttonId)).perform(ViewActions.click())
        return this
    }

    fun swipeDirection(direction: Int, view: Int) {
        when (direction) {
            DIRECTION_UP -> ViewActions.swipeUp()
            DIRECTION_RIGHT -> onView(ViewMatchers.withId(view)).perform(ViewActions.swipeRight())
            DIRECTION_DOWN -> ViewActions.swipeDown()
            DIRECTION_LEFT -> onView(ViewMatchers.withId(view)).perform(ViewActions.swipeLeft())
            else -> {
                Timber.v("Unknown swipe direction")
            }
        }

    }
}