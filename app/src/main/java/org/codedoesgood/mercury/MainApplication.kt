package org.codedoesgood.mercury

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

import com.crashlytics.android.Crashlytics

import org.codedoesgood.mercury.onboarding.viewmodel.OnboardingViewModel
import org.codedoesgood.mercury.projectlist.viewmodel.ProjectListViewModel
import org.codedoesgood.mercury.viewmodel.MainActivityViewModel

import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 * Default launch application
 */
class MainApplication : Application() {

    companion object {
        lateinit var context: Context

        fun getAppContext() = context
    }

    var mainActivityViewModel = MainActivityViewModel()
    var projectListViewModel = ProjectListViewModel()
    var onboardingViewModel = OnboardingViewModel()

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
        }
    }



}