package org.codedoesgood.mercury.mainactivitytests.onboarding.tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.codedoesgood.mercury.R
import org.codedoesgood.mercury.api.MockApiResponseController
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
    fun fragmentsLoaded() {
        Thread.sleep(3000)
        OnboardingRobot().performActions {
            checkViewWithIdVisible(R.id.login_username)
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            checkViewWithIdVisible(R.id.reg_email)
        }
    }

    fun loginNonExistentUser_Fail() {
        MockApiResponseController.currentTest =
                MockApiResponseController.Companion.loginNonExistentUser_Fail

        OnboardingRobot().performActions {
            enterTextInField("NonexistentUser",R.id.login_username)
            enterTextInField("NonexistentPassword",R.id.login_password)
            clickButton(R.id.button_login)
            checkForToastContainingText("does not exist", activityRule)
        }
    }

    fun loginExistingUser_Success() {
        MockApiResponseController.currentTest =
                MockApiResponseController.Companion.loginExistingUser_Success

        OnboardingRobot().performActions {
            enterTextInField("brandonpas",R.id.login_username)
            enterTextInField("something",R.id.login_password)
            clickButton(R.id.button_login)
            checkViewWithIdVisible(R.id.project_list_recycler_view)
        }
    }

    fun registerUserExistingEmail_Fail() {

    }

    fun registerUserExistingUsername_Fail() {

    }

    fun registerUser_Success() {
        
    }
}