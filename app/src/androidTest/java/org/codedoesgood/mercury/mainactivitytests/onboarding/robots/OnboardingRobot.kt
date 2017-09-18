package org.codedoesgood.mercury.mainactivitytests.onboarding.robots

import org.codedoesgood.mercury.Robot


class OnboardingRobot : Robot() {

    /**
     * Enhanced builder method. Allows passing in functions as parameters which are
     * applied on the robot object.
     */
    fun performActions(func: OnboardingRobot.() -> Unit) = OnboardingRobot().apply { func() }
}