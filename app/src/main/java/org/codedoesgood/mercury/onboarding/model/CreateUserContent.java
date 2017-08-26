package org.codedoesgood.mercury.onboarding.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the <b>volunteer</b> JSON object within the {@link CreateUserPayload}
 */
public class CreateUserContent {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email_address")
    @Expose
    private String email;

    @SerializedName("data_entry_user_id")
    @Expose
    private String entryID;

    /**
     * Builder to set the "username" property
     * @param newUsername Registration username
     * @return Returns the {@code CreateUserContent} object
     */
    public CreateUserContent setUsername(String newUsername) {
        this.username = newUsername;
        this.entryID = newUsername;
        return this;
    }

    /**
     * Builder to set the "password" property
     * @param newPassword Registration password
     * @return Returns the {@code CreateUserContent} object
     */
    public CreateUserContent setPassword(String newPassword) {
        this.password = newPassword;
        return this;
    }

    /**
     * Builder to set the "email" property
     * @param newEmail  Registration email address
     * @return Returns the {@code CreateUserContent} object
     */
    public CreateUserContent setEmail(String newEmail) {
        this.email = newEmail;
        return this;
    }

    /**
     * Builder to set the "name" property
     * @param newName Registration user's first and last name
     * @return Returns the {@code CreateUserContent} object
     */
    public CreateUserContent setName(String newName) {
        this.name = newName;
        return this;
    }

    /**
     * Utilizes the {@code CreateUserContent} object to create the
     * {@code CreateUserPayload} object
     * @return Returns {@link CreateUserPayload}
     */
    public CreateUserPayload buildPayload() {
        return new CreateUserPayload(this);
    }
}
