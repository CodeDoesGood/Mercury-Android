package org.codedoesgood.mercury.model;

import org.codedoesgood.mercury.projectlist.model.ProjectsPayload;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Retrofit interface for retrieving Project List information
 */
public interface CDGApiService {

    /**
     * Retrieve all active projects
     * @return An Observable object representing the API response payload.
     */
    @GET("api/projects/all/active")
    Observable<ProjectsPayload> getAllActiveProjects();
}
