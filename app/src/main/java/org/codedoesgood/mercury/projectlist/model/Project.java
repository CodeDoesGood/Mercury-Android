package org.codedoesgood.mercury.projectlist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data model for a CDG project
 */
public class Project {

    /**
     * The title of the project
     */
    @SerializedName("title")
    private String projectTitle;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
