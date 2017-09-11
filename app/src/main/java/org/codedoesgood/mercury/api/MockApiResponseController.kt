package org.codedoesgood.mercury.api

class MockApiResponseController {

    companion object {
        var currentTest = -1
        var currentResponseCode = 0

        val loginNonExistentUser_Fail = 0
        val loginExistingUser_Success = 1

        val top = "mock-responses"

        fun getResponseCode() = when (currentTest) {
            loginNonExistentUser_Fail -> 501
            loginExistingUser_Success -> 200
            else -> 404
        }

        fun getResponseFilePath(): String {
            val temp = currentTest
            currentTest = -1
            currentResponseCode = 0
            return getResponseFilePath(temp)
        }

        fun getResponseFilePath(testNumber: Int): String = when (testNumber) {
            loginNonExistentUser_Fail -> "${top}/authenticate/standard_nonexisting_user_fail.json"
            loginExistingUser_Success -> "${top}/authenticate/standard_existing_user_success.json"
            else -> ""
        }
    }
}