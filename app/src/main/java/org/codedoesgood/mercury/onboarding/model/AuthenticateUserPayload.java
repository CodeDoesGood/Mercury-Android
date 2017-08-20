package org.codedoesgood.mercury.onboarding.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the JSON object sent when authenticating a user login
 * {@link org.codedoesgood.mercury.api.ApiService#authenticateUser(AuthenticateUserPayload)}}
 */
public class AuthenticateUserPayload {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    /**
     * Constructor for the payload
     * @param user Username to authenticate
     * @param pass Password to authenticate
     */
    public AuthenticateUserPayload(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

}
