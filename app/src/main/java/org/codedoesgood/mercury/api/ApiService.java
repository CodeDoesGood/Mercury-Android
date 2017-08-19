package org.codedoesgood.mercury.api;

import org.codedoesgood.mercury.onboarding.model.CreateUserPayload;
import org.codedoesgood.mercury.onboarding.model.CreateUserResponse;
import org.codedoesgood.mercury.projectlist.model.ProjectsResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Retrofit interface for retrieving Project List information
 */
public interface ApiService {

    /**
     * Retrieve all active projects
     * @return An Observable object representing the API response payload.
     */
    @GET("projects/all/active")
    Observable<ProjectsResponse> getAllActiveProjects();

    /**
     * Create a user
     * @param volunteer The {@link CreateUserPayload} representing the "volunteer" JSON object
     * @return A {@link CreateUserResponse} Observable
     */
    @POST("volunteer/create")
    Observable<CreateUserResponse> createUser(@Body CreateUserPayload volunteer);

}
