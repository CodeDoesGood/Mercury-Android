package org.codedoesgood.mercury.utilities;

/**
 * Static class for constants needed for any APIs
 */
public final class ApiConstants {

    /**
     * Base URL for mercury web service. Until this is resides on a functional server, you must use your machines IP.
     * Using localhost will not work as this resolve to the emulator/devices loopback address.
     */
    public static final String MERCURY_BASE_URL = "http://10.0.0.119:3000/";

    private ApiConstants() { }
}
