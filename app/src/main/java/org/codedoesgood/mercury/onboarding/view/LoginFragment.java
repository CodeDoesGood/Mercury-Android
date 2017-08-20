package org.codedoesgood.mercury.onboarding.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.onboarding.model.AuthenticateUserResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Represents the Login screen
 */
public class LoginFragment extends Fragment {

    private EditText viewUsername;
    private EditText viewPassword;
    private CompositeDisposable compositeDisposable;
    private OnboardingActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity = (OnboardingActivity) getActivity();
        initViews();
        bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        unbind();
    }

    /**
     * Initialize views, attach adapters and listeners.
     */
    public void initViews() {
        View accountExists = activity.findViewById(R.id.button_register);
        Button buttonSubmitAuth = activity.findViewById(R.id.button_login);

        viewUsername = activity.findViewById(R.id.login_username);
        viewPassword = activity.findViewById(R.id.login_password);

        accountExists.setOnClickListener(view ->
            activity.setViewPagerCurrentItem(OnboardingActivity.FRAGMENT_REGISTRATION));

        buttonSubmitAuth.setOnClickListener(view -> authenticateUser());
    }

    /**
     * Create observers and subscribe to observables. Called from
     * {@link #onResume()}
     */
    public void bind() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                activity.getOnboardingViewModel().getAuthUserObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getAuthObserver()));
    }

    /**
     * Dispose of observers. Called from {@link #onPause()}
     */
    public void unbind() {
        compositeDisposable.dispose();
    }

    /**
     * Call the function in the ViewModel to authenticate the user
     */
    public void authenticateUser() {

        String username = viewUsername.getText().toString();
        String password = viewPassword.getText().toString();

        Timber.v("authenticateUser called username: " + username);
        activity.getOnboardingViewModel().authenticateUser(username, password);
    }

    /**
     * Obtain a DisposableObserver for the authentication response
     * @return DisposableObserver
     */
    public DisposableObserver<AuthenticateUserResponse> getAuthObserver() {
        return new DisposableObserver<AuthenticateUserResponse>() {
            @Override
            public void onNext(@NonNull AuthenticateUserResponse authenticateUserResponse) {
                Timber.v("onNext ");

                if (!authenticateUserResponse.getIsError()) {
                    if (authenticateUserResponse.getAuthToken().length() > 0) {
                        activity.launchProjectList();
                    } else {
                        activity.displayToast("Unable to authenticate", Toast.LENGTH_SHORT);
                    }
                } else {
                    activity.displayToast(authenticateUserResponse.getMessage(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.v("onError " + e.getMessage());
            }

            @Override
            public void onComplete() { }
        };
    }

}
