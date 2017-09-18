package org.codedoesgood.mercury.projectlist.view

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.codedoesgood.mercury.MainApplication
import org.codedoesgood.mercury.R
import org.codedoesgood.mercury.onboarding.view.OnboardingActivity
import org.codedoesgood.mercury.projectlist.model.Project
import org.codedoesgood.mercury.projectlist.viewmodel.ProjectListViewModel
import timber.log.Timber
import java.util.ArrayList

/**
 * Activity for the ProjectList screen
 */
class ProjectListActivity : AppCompatActivity() {

    private var projectListViewModel: ProjectListViewModel? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var projectAdapter: ProjectListAdapter? = null
    private var projectListProgressBar: ProgressBar? = null
    private var projectListLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.logout_menu_item -> {
            logout()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        unbind()
    }

    override fun onResume() {
        super.onResume()

        val application = application as MainApplication
        projectListViewModel = application.projectListViewModel
        initViews()
        bind()
    }

    /**
     * Launch the activity for the login screen and close the current activity
     */
    fun logout() {
        startActivity(Intent(this,OnboardingActivity::class.java))
        finish()
    }

    /**
     * Initialize any views and attach any listeners/adapters here
     */
    fun initViews() {
        val projectListView = findViewById<RecyclerView>(R.id.project_list_recycler_view)
        projectAdapter = ProjectListAdapter(projectListViewModel)
        projectListView.adapter = projectAdapter
        projectListView.layoutManager = LinearLayoutManager(applicationContext)

        projectListProgressBar = findViewById(R.id.project_list_progress_bar)
        projectListLayout = findViewById(R.id.views_container)
        hideProgress()

        val testButton = findViewById<Button>(R.id.temporary_button)
        testButton.setOnClickListener { view ->
            showProgress()
            projectListViewModel!!.retrieveAllActiveProjects()
        }
    }

    /**
     * Hide the progress bar
     */
    fun hideProgress() {
        projectListProgressBar!!.visibility = View.INVISIBLE
        projectListLayout!!.visibility = View.VISIBLE
    }

    /**
     * Show the progress bar
     */
    fun showProgress() {
        projectListProgressBar!!.visibility = View.VISIBLE
        projectListLayout!!.visibility = View.INVISIBLE
    }

    /**
     * Subscribe to observables here. Called in onResume()
     */
    fun bind() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(projectListViewModel!!.projectListObservable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(projectListResult()))
    }

    /**
     * Dispose of subscriptions. Called from  onPause()
     */
    fun unbind() {
        compositeDisposable!!.dispose()
    }

    /**
     * Used as a callback for when the **projectObservable** in [ProjectListViewModel] emits a change
     * @return DisposableObserver for type ArrayList of Project
     */
    fun projectListResult(): DisposableObserver<ArrayList<Project>> {
        return object : DisposableObserver<ArrayList<Project>>() {

            override fun onNext(@NonNull projects: ArrayList<Project>) {
                hideProgress()
                projectAdapter!!.notifyDataSetChanged()
            }

            override fun onError(@NonNull e: Throwable) {
                Timber.e(e.message)
                hideProgress()
                Toast.makeText(applicationContext, "Unable to retrieve data.", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {
                hideProgress()
            }
        }
    }
}
