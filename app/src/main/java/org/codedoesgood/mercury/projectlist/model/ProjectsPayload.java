package org.codedoesgood.mercury.projectlist.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * API payload response
 */
public class ProjectsPayload {

    @SerializedName("content")
    private ProjectPayloadContent projectContent;

    public ProjectPayloadContent getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(ProjectPayloadContent projectContent) {
        this.projectContent = projectContent;
    }

    public ArrayList<Project> getProjectList() { return projectContent.projectArrayList; }

    private class ProjectPayloadContent {
        @SerializedName("projects")
        private ArrayList<Project> projectArrayList;

        public ArrayList<Project> getProjectArrayList() {
            return projectArrayList;
        }

        public void setProjectArrayList(ArrayList<Project> projectArrayList) {
            this.projectArrayList = projectArrayList;
        }
    }
}
