package org.codedoesgood.mercury.api;

/**
 * Helper class for the API endpoint
 */
public final class ApiUtility {

    /**
     * Base URL for Mercury web service. Until this is resides on a functional server, you must use your machines IP.
     * Using localhost will not work as this resolve to the emulator/devices loopback address.
     */
    private static final String MERCURY_BASE_URL = "http://10.0.0.119:3000/api/";

    private ApiUtility() { }

    /**
     * Get the base URL for the Mercury web service.
     * @return The base URL
     */
    static String getMercuryBaseUrl() { return MERCURY_BASE_URL; }

    /**
     * Get an implementation of the API endpoint.
     * @return An implementation of the API endpoint
     */
    public static ApiService getApiService() {
        return RetrofitClient.getClient().create(ApiService.class);
    }
}
