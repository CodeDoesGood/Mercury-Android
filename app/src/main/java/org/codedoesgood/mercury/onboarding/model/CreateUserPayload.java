package org.codedoesgood.mercury.onboarding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the JSON object sent when registering a user
 * {@link org.codedoesgood.mercury.api.ApiService#createUser(CreateUserPayload)}
 */
public class CreateUserPayload {

    @SerializedName("volunteer")
    @Expose
    private CreateUserContent volunteer;

    /**
     * Constructor for the payload
     * @param content A predefined {@code CreateUserContent} object.
     */
    CreateUserPayload(CreateUserContent content) { this.volunteer = content; }

    public CreateUserContent getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(CreateUserContent volunteer) {
        this.volunteer = volunteer;
    }
}
