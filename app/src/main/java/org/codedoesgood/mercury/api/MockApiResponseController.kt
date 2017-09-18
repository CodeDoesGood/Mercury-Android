package org.codedoesgood.mercury.api

class MockApiResponseController {

    companion object {

        // Test types
        val noActiveTest = -1
        val loginNonExistentUser_Fail = 0
        val loginExistingUser_Success = 1
        val registration_ExistingEmail_Fail = 2
        val registration_ExistingUsername_Fail = 3
        val registration_Success = 4

        val top = "mock-responses"
        var currentTest = noActiveTest
        var testsToRun = arrayOf(currentTest)

        // Response codes
        val invalidResponse = 404
        val successWithServerError = 503
        val success = 200

        fun defineTestsToRun(array: Array<Int>) {
            currentTest = array[0]

            testsToRun = if (array.size > 1) {
                array.copyOfRange(1, array.size)
            } else {
                arrayOf(noActiveTest)
            }
        }

        fun getResponseCode() = when (currentTest) {
            loginNonExistentUser_Fail -> successWithServerError
            loginExistingUser_Success -> success
            registration_ExistingEmail_Fail -> successWithServerError
            registration_ExistingUsername_Fail -> successWithServerError
            registration_Success -> successWithServerError
            else -> invalidResponse
        }

        fun getResponseFilePath(): String {
            val runningTest = currentTest

            // Pop off the current test, shift tests to the left, and update remaining tests
            defineTestsToRun(testsToRun)
            return getResponseFilePath(runningTest)
        }

        private fun getResponseFilePath(testNumber: Int): String = when (testNumber) {
            loginNonExistentUser_Fail -> "$top/authenticate/standard_nonexisting_user_fail.json"
            loginExistingUser_Success -> "$top/authenticate/standard_existing_user_success.json"
            registration_ExistingEmail_Fail -> "$top/create/registration_existing_email.json"
            registration_ExistingUsername_Fail -> "$top/create/registration_existing_username.json"
            registration_Success -> "$top/create/registration_success.json"
            else -> ""
        }
    }
}