package org.codedoesgood.mercury;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import org.codedoesgood.mercury.viewmodel.MainActivityViewModel;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Default launch application
 */
public class MainApplication extends Application {

    private MainActivityViewModel mainActivityViewModel = new MainActivityViewModel();

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
        }
    }

    public MainActivityViewModel getMainActivityViewModel() {
        return mainActivityViewModel;
    }
}
