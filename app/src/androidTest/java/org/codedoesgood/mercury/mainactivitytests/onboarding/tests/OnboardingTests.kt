package org.codedoesgood.mercury.mainactivitytests.onboarding.tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.codedoesgood.mercury.R
import org.codedoesgood.mercury.mainactivitytests.onboarding.robots.OnboardingRobot
import org.codedoesgood.mercury.onboarding.view.OnboardingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingTests {

    @get:Rule
    var activityRule = ActivityTestRule(OnboardingActivity::class.java)

    @Test
    fun verifyFragmentsLoaded() {
        OnboardingRobot().performActions {
            checkViewWithIdVisible(R.id.login_username)
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            checkViewWithIdVisible(R.id.reg_email)
        }
    }


    fun loginNonExistentUser_Fail() {

    }

    fun loginExistingUser_Success() {

    }

    fun registerUserExistingEmail_Fail() {

    }

    fun registerUserExistingUsername_Fail() {

    }

    fun registerUser_Success() {

    }
}