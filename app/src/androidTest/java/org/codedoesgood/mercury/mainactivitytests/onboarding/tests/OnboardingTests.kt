package org.codedoesgood.mercury.mainactivitytests.onboarding.tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.codedoesgood.mercury.R
import org.codedoesgood.mercury.api.MockApiResponseController
import org.codedoesgood.mercury.mainactivitytests.onboarding.robots.OnboardingRobot
import org.codedoesgood.mercury.onboarding.view.OnboardingActivity
import org.junit.runner.RunWith
import org.junit.*

@RunWith(AndroidJUnit4::class)
class OnboardingTests {

    @get:Rule
    var activityRule = ActivityTestRule(OnboardingActivity::class.java)

    @Test
    fun fragmentsLoaded() {
        OnboardingRobot().performActions {
            checkViewWithIdVisible(R.id.login_username)
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            checkViewWithIdVisible(R.id.reg_email)
        }
    }

    @Test
    fun loginNonExistentUser_Fail() {
        val testsToRun = arrayOf(MockApiResponseController.Companion.loginNonExistentUser_Fail)
        MockApiResponseController.defineTestsToRun(testsToRun)

        OnboardingRobot().performActions {
            enterTextInField("NonexistentUser",R.id.login_username)
            enterTextInField("NonexistentPassword",R.id.login_password)
            clickButton(R.id.button_login)
            checkForToastContainingText("does not exist", activityRule)
        }
    }

    @Test
    fun loginExistingUser_Success() {
        val testsToRun = arrayOf(MockApiResponseController.Companion.loginExistingUser_Success)
        MockApiResponseController.defineTestsToRun(testsToRun)

        OnboardingRobot().performActions {
            enterTextInField("ExistingUser", R.id.login_username)
            enterTextInField("ValidPassword", R.id.login_password)
            clickButton(R.id.button_login)
            checkViewWithIdVisible(R.id.project_list_constraint_layout)
        }
    }

    @Test
    fun registration_ExistingEmail_Fail() {
        val testsToRun = arrayOf(MockApiResponseController.Companion.registration_ExistingEmail_Fail)
        MockApiResponseController.defineTestsToRun(testsToRun)

        OnboardingRobot().performActions {
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            enterTextInField("SomeName", R.id.reg_name)
            enterTextInField("NewUserName", R.id.reg_username)
            enterTextInField("ExistingEmail", R.id.reg_email)
            enterTextInField("ValidPassword", R.id.reg_password)
            clickButton(R.id.button_reg_submit)
            checkForToastContainingText("already exist", activityRule)
        }
    }

    @Test
    fun registration_ExistingUsername_Fail() {

        val testsToRun = arrayOf(MockApiResponseController.Companion.registration_ExistingUsername_Fail)
        MockApiResponseController.defineTestsToRun(testsToRun)

        OnboardingRobot().performActions {
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            enterTextInField("SomeName", R.id.reg_name)
            enterTextInField("ExistingUserName", R.id.reg_username)
            enterTextInField("NewEmail", R.id.reg_email)
            enterTextInField("ValidPassword", R.id.reg_password)
            clickButton(R.id.button_reg_submit)
            checkForToastContainingText("already exist", activityRule)
        }
    }

    @Test
    fun registration_Success() {
        val testsToRun = arrayOf(MockApiResponseController.Companion.registration_Success,
                MockApiResponseController.Companion.loginExistingUser_Success)

        MockApiResponseController.defineTestsToRun(testsToRun)

        OnboardingRobot().performActions {
            swipeDirection(DIRECTION_LEFT, R.id.login_constraint_layout)
            enterTextInField("SomeName", R.id.reg_name)
            enterTextInField("NewUserName", R.id.reg_username)
            enterTextInField("NewEmail", R.id.reg_email)
            enterTextInField("ValidPassword", R.id.reg_password)
            clickButton(R.id.button_reg_submit)
            checkViewWithIdVisible(R.id.project_list_constraint_layout)
        }
    }
}