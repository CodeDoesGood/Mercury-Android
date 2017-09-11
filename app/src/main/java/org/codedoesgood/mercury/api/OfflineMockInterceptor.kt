package org.codedoesgood.mercury.api

import okhttp3.*
import org.codedoesgood.mercury.MainApplication
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Mock interceptor for testing API calls without a server
 */
class OfflineMockInterceptor : Interceptor {

    companion object {
        private val MEDIA_JSON = MediaType.parse("application/json")
    }

    /**
     * Build a response based on the API call used
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        /* http://sample.com/hello will return "/hello" */
//        val path = request.url().encodedPath()

        /* I put a 'hello' file in debug/assets/mockResponses */
//        val pathArray = path.split("/api/")
//        val fileName = "mockResponses/${pathArray[1]}.json"
        val responseCode = MockApiResponseController.Companion.getResponseCode()
        val fileName = MockApiResponseController.Companion.getResponseFilePath()
        Timber.v("FileName: $fileName")
        val stream = MainApplication.Companion.getAppContext().assets.open(fileName)

        /* Just read the file. */
        val json = parseStream(stream)

        val resp = Response.Builder()
                .body(ResponseBody.create(MEDIA_JSON, json))
                .message("Mock Default Message")
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(responseCode)
                .build()

        Timber.v("response built")
        return resp
    }

    /**
     * Read response from the file
     */
    @Throws(IOException::class)
    private fun parseStream(stream: InputStream): String {
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line)
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }
}