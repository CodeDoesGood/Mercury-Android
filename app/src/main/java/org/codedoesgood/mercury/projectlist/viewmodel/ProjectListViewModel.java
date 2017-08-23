package org.codedoesgood.mercury.projectlist.viewmodel;

import org.codedoesgood.mercury.utilities.ApiConstants;
import org.codedoesgood.mercury.projectlist.model.Project;
import org.codedoesgood.mercury.model.CDGApiService;
import org.codedoesgood.mercury.projectlist.model.ProjectsPayload;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * ViewModel for the ProjectListActivity
 */
public class ProjectListViewModel {

    private ArrayList<Project> projectList = new ArrayList<>();
    private BehaviorSubject<ArrayList<Project>> projectObservable = BehaviorSubject.create();

    /**
     * Used by the ProjectActivity to subscribe to changes to the projectList array.
     * @return Observable representing the project ArrayList.
     */
    public Observable<ArrayList<Project>> getProjectListObservable() {
        return projectObservable;
    }

    /**
     * Makes the API call to retrieve all active projects.
     */
    public void retrieveAllActiveProjects() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.MERCURY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CDGApiService apiService = retrofit.create(CDGApiService.class);
        apiService.getAllActiveProjects()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectsPayload>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull ProjectsPayload payload) {
                        updateList(payload.getProjectList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e.getMessage());
                        projectObservable.onError(e);
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    /**
     * Called when the API response is received to update the list of projects and emit the changes.
     * @param list
     */
    private void updateList(ArrayList<Project> list) {
        projectList.clear();
        projectList.addAll(list);
        projectObservable.onNext(list);
    }

    /**
     * Retreive the size of the projectList array. Used by the ProjectListAdapter.
     * @return The current size of the projectList array.
     */
    public int getProjectListArraySize() { return projectList.size(); }

    /**
     * Retrieve the Project object at the specified position. Used by the ProjectListAdapter.
     * @param position The location in the projectList array.
     * @return The Project at the specified position.
     */
    public Project getProjectAtPosition(int position) { return projectList.get(position); }
}
