package org.codedoesgood.mercury.onboarding.viewmodel;

import org.codedoesgood.mercury.api.ApiError;
import org.codedoesgood.mercury.api.ApiErrorUtility;
import org.codedoesgood.mercury.api.ApiUtility;
import org.codedoesgood.mercury.onboarding.model.AuthenticateUserPayload;
import org.codedoesgood.mercury.onboarding.model.AuthenticateUserResponse;
import org.codedoesgood.mercury.onboarding.model.CreateUserPayload;
import org.codedoesgood.mercury.onboarding.model.CreateUserResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * ViewModel for the OnboardingActivity
 */
public class OnboardingViewModel {

    private PublishSubject<CreateUserResponse> createUserResponse = PublishSubject.create();
    private PublishSubject<AuthenticateUserResponse> authUserResponseObservable
            = PublishSubject.create();

    /**
     * API call to create a user and notify Observers of the response
     * @param content {@link CreateUserPayload} representing the
     *        API request
     */
    public void registerUser(CreateUserPayload content) {
        Timber.v("registerUser called");

        // Need to validate credentials
        // For now just create the user

        ApiUtility.getApiService().createUser(content)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<CreateUserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull CreateUserResponse response) {
                        Timber.v("onNext called");
                        createUserResponse.onNext(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.v("onError called " + e.getMessage());
                        createUserError(e);
                    }

                    @Override
                    public void onComplete() { Timber.v("onComplete called"); }
                });
    }

    /**
     * Retreive Subject observable for the Create User Request
     * @return Subject bservable
     */
    public PublishSubject<CreateUserResponse> getCreateUserObservable() {
        return createUserResponse;
    }

    /**
     * Extracts the error message, constructs an "error" instance of {@link CreateUserResponse} and
     * notifies Observers.
     * @param throwable The {@link Throwable} returned to the observer
     */
    private void createUserError(Throwable throwable) {
            HttpException exception = (HttpException) throwable;
            ApiError apiError = ApiErrorUtility.parseError(exception.response());
            CreateUserResponse errResponse = new CreateUserResponse()
                    .setErrorDescription(apiError.getDescription())
                    .setError(apiError.getError());

            if (((HttpException) throwable).code() != 503) {
                errResponse.setIsError(true);
            }

            Timber.v(apiError.getDescription());
            createUserResponse.onNext(errResponse);
    }

    /**
     * Retreive the Subject observable for Authenticate User request
     * @return Subject observable
     */
    public PublishSubject<AuthenticateUserResponse> getAuthUserObservable() {
        return authUserResponseObservable;
    }

    /**
     * API call to authenticate user and notify Observers of
     * the response
     * @param username The username as String
     * @param password The password as String
     */
    public void authenticateUser(String username, String password) {

        // Need to validate credentials
        // For now just authenticate the user

        AuthenticateUserPayload payload = new
                AuthenticateUserPayload(username, password);

        Timber.v("authenticateUser called username: " + username);
        Observable<AuthenticateUserResponse> respObserv = ApiUtility.getApiService().authenticateUser(payload);
        respObserv.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthenticateUserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull AuthenticateUserResponse authenticateUserResponse) {
                        Timber.v("on Next() called " + authenticateUserResponse.getMessage());
                        authUserResponseObservable.onNext(authenticateUserResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.v("on Error() called message: " + e.getMessage());
                        Timber.v("on Error() called cause: " + e.getCause());
                        authUserError(e);
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    /**
     * Extracts the error message, constructs an "error" instance of
     * {@link AuthenticateUserResponse} and notifies Observers.
     * @param throwable The {@link Throwable} returned to the observer
     */
    private void authUserError(Throwable throwable) {
            HttpException exception = (HttpException) throwable;
            ApiError apiError = ApiErrorUtility.parseError(exception.response());

            AuthenticateUserResponse response = new AuthenticateUserResponse();
            response.setIsError(true)
                    .setMessage(apiError.getDescription());

            Timber.v(apiError.getDescription());
            authUserResponseObservable.onNext(response);
    }
}
