package org.codedoesgood.mercury.onboarding.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.onboarding.model.CreateUserContent;
import org.codedoesgood.mercury.onboarding.model.CreateUserPayload;
import org.codedoesgood.mercury.onboarding.model.CreateUserResponse;
import org.codedoesgood.mercury.onboarding.viewmodel.OnboardingViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Interface for user registration
 */
public class RegistrationFragment extends Fragment {

    private EditText viewName;
    private EditText viewUsername;
    private EditText viewEmail;
    private EditText viewPassword;
    private CompositeDisposable compositeDisposable;
    private OnboardingActivity activity;
    private OnboardingViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
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
        activity = (OnboardingActivity) getActivity();
        viewModel = activity.getOnboardingViewModel();

        TextView accountExists = activity.findViewById(R.id.label_has_account);
        accountExists.setOnClickListener(view ->
            activity.setViewPagerCurrentItem(OnboardingActivity.Companion.getFRAGMENT_LOGIN()));

        viewName = activity.findViewById(R.id.reg_name);
        viewUsername = activity.findViewById(R.id.reg_username);
        viewEmail = activity.findViewById(R.id.reg_email);
        viewPassword = activity.findViewById(R.id.reg_password);

        Button buttonSubmitRegistration = activity.findViewById(R.id.button_reg_submit);
        buttonSubmitRegistration.setOnClickListener(view -> {
            Timber.v("register button clicked");

            // Validation will occur in ViewModel
            String username = viewUsername.getText().toString();
            String name = viewName.getText().toString();
            String password = viewPassword.getText().toString();
            String email = viewEmail.getText().toString();

            CreateUserPayload content = new CreateUserContent()
                    .setUsername(username)
                    .setPassword(password)
                    .setEmail(email)
                    .setName(name)
                    .buildPayload();


            viewModel.registerUser(content);
        });

    }

    /**
     * Create observers and subscribe to observables. Called from
     * {@link #onResume()}
     */
    public void bind() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                viewModel.getCreateUserObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(userResponseObserver())
        );
    }

    /**
     * Dispose of observers. Called from {@link #onPause()}
     */
    public void unbind() {
        compositeDisposable.dispose();
    }

    /**
     * Retrieve a {@code DisposableObserver} of the {@code CreateUserResponse}
     * for the {@link OnboardingViewModel#getCreateUserObservable()}
     * @return Instance of a DisposableObserver
     */
    public DisposableObserver<CreateUserResponse> userResponseObserver() {
       return new DisposableObserver<CreateUserResponse>() {
           @Override
           public void onNext(@NonNull CreateUserResponse response) {

               String error = response.getError();
               Timber.v("onNext called: " + error);
               if (error.equals("Email Service")) {
                   viewModel.authenticateUser(viewUsername.getText().toString(),
                           viewPassword.getText().toString());
               } else {
                   activity.displayToast(response.getErrorDescription(), Toast.LENGTH_SHORT);
               }
           }

           @Override
           public void onError(@NonNull Throwable e) {
               Timber.v("onError called");
               activity.displayToast(e.getMessage(), Toast.LENGTH_SHORT);
           }

           @Override
           public void onComplete() { }
       };
    }

}
