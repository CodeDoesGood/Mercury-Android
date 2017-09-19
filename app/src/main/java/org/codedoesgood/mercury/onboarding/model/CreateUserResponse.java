package org.codedoesgood.mercury.onboarding.model;

import com.google.gson.annotations.SerializedName;

/**
 * Java representation of the response from the API
 * {@link org.codedoesgood.mercury.api.ApiService#createUser(CreateUserPayload)}
 */
public class CreateUserResponse {

    @SerializedName("id")
    private String newUserId;

    @SerializedName("code")
    private String verifyCode;

    @SerializedName("error")
    private String error;

    @SerializedName("description")
    private String errorDescription;

    private boolean isError = false;

    private String getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(String newUserId) {
        this.newUserId = newUserId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * Get the general error type
     * @return The top level error message type
     */
    public String getError() {
        if (error == null) {
            error = "";
        }
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public boolean isError() {
        return isError;
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param error Flag indicating the response is an error.
     * @return The {@code CreateUserResponse} object
     */
    public CreateUserResponse setIsError(boolean error) {
        isError = error;
        return this;
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param errorDescription The detailed description of the error
     * @return The {@code CreateUserResponse} object
     */
    public CreateUserResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    /**
     * Builder used for manually constructing an response payload when the API
     * encounters an error
     * @param error Top level error message
     * @return The {@code CreateUserResponse} object
     */
    public CreateUserResponse setError(String error) {
        this.error = error;
        return this;
    }
}
