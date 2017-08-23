package org.codedoesgood.mercury.projectlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.codedoesgood.mercury.MainApplication;
import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.projectlist.model.Project;
import org.codedoesgood.mercury.projectlist.viewmodel.ProjectListViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Activity for the ProjectList screen
 */
public class ProjectListActivity extends AppCompatActivity {

    private ProjectListViewModel projectListViewModel;
    private CompositeDisposable compositeDisposable;
    private ProjectListAdapter projectAdapter;
    private ProgressBar projectListProgressBar;
    private ConstraintLayout projectListLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MainApplication application = (MainApplication) getApplication();
        projectListViewModel = application.getProjectListViewModel();
        initViews();
        bind();
    }

    /**
     * Initialize any views and attach any listeners/adapters here
     */
    public void initViews() {
        RecyclerView projectListView = findViewById(R.id.project_list_recycler_view);
        projectAdapter = new ProjectListAdapter(projectListViewModel);
        projectListView.setAdapter(projectAdapter);
        projectListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        projectListProgressBar = findViewById(R.id.project_list_progress_bar);
        projectListLayout = findViewById(R.id.views_container);
        hideProgress();

        Button testButton = findViewById(R.id.temporary_button);
        testButton.setOnClickListener(view -> {
                showProgress();
                projectListViewModel.retrieveAllActiveProjects();
        });
    }

    /**
     * Hide the progress bar
     */
    public void hideProgress() {
        projectListProgressBar.setVisibility(View.INVISIBLE);
        projectListLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Show the progress bar
     */
    public void showProgress() {
        projectListProgressBar.setVisibility(View.VISIBLE);
        projectListLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Subscribe to observables here. Called in onResume()
     */
    public void bind() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(projectListViewModel.getProjectListObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(projectListResult()));
    }

    /**
     * Dispose of subscriptions. Called from  onPause()
     */
    public void unbind() {
        compositeDisposable.dispose();
    }

    /**
     * Used as a callback for when the <b>projectObservable</b> in {@link ProjectListViewModel} emits a change
     * @return DisposableObserver for type ArrayList of Project
     */
    public DisposableObserver<ArrayList<Project>> projectListResult() {
        return new DisposableObserver<ArrayList<Project>>() {

            @Override
            public void onNext(@NonNull ArrayList<Project> projects) {
                hideProgress();
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e.getMessage());
                hideProgress();
                Toast.makeText(getApplicationContext(), "Unable to retrieve data.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                hideProgress();
            }
        };
    }
}
