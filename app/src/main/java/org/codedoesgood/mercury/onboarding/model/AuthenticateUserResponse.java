package org.codedoesgood.mercury.onboarding.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the JSON object received when authenticating a user login
 * {@link org.codedoesgood.mercury.api.ApiService#authenticateUser(AuthenticateUserPayload)}
 */
public class AuthenticateUserResponse {

    /**
     * Top level message returned if the authentication was successful
     */
    @SerializedName("message")
    private String message;

    /**
     * JSON object containing the token on successful authentication
     */
    @SerializedName("content")
    private AuthToken content;

    /**
     * Top level error message
     */
    @SerializedName("error")
    private String error;

    /**
     * Received with the username does not exist
     */
    @SerializedName("description")
    private String errorDescription;

    private boolean isError = false;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthToken getContent() {
        return content;
    }

    public void setContent(AuthToken content) {
        this.content = content;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public boolean getIsError() {
        return isError;
    }

    public String getAuthToken() {
        return this.content.getToken();
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param error Flag indicating the response is an error.
     * @return The {@code AuthenticateUserResponse} object
     */
    public AuthenticateUserResponse setIsError(boolean error) {
        this.isError = error;
        return this;
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param error Top level error message
     * @return The {@code AuthenticateUserResponse} object
     */
    public AuthenticateUserResponse setError(String error) {
        this.error = error;
        return this;
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param errorDescription Description of the error encountered
     * @return The {@code AuthenticateUserResponse} object
     */
    public AuthenticateUserResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    /**
     * Token received upon successful authentication of user
     */
    class AuthToken {
        @SerializedName("token")
        private String token;

        public String getToken() {
            return this.token;
        }
    }
}
