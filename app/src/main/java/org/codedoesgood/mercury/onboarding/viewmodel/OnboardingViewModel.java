package org.codedoesgood.mercury.onboarding.viewmodel;

import org.codedoesgood.mercury.api.ApiUtility;
import org.codedoesgood.mercury.onboarding.model.CreateUserPayload;
import org.codedoesgood.mercury.onboarding.model.CreateUserResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * ViewModel for the OnboardingActivity
 */
public class OnboardingViewModel {

    private BehaviorSubject<CreateUserResponse> createUserResponse = BehaviorSubject.create();

    /**
     * API call to create a user and notify Observers of the response
     * @param content {@link CreateUserPayload} representing the
     *        API request
     */
    public void registerUser(CreateUserPayload content) {
        Timber.v("registerUser called");

        ApiUtility.getApiService().createUser(content)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<CreateUserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull CreateUserResponse createUserResponse) {
                        Timber.v("onNext called");
                        notifyObservers(createUserResponse);
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

    public Observable<CreateUserResponse> getCreateUserObservable() { return createUserResponse; }

    /**
     * Pass the {@link CreateUserResponse} to any Observers
     * @param response The {@code CreateUserResponse} returned by
     * the {@link org.codedoesgood.mercury.api.ApiService#createUser(CreateUserPayload)}
     */
    private void notifyObservers(CreateUserResponse response) {
        Timber.v("notifyObservers called");
        createUserResponse.onNext(response);
    }

    /**
     * Extracts the error message, constructs an "error" instance of {@link CreateUserResponse} and
     * notifies Observers.
     * @param throwable The {@link Throwable} returned to the observer
     */
    private void createUserError(Throwable throwable) {
        try {
            HttpException exception = (HttpException) throwable;
            ResponseBody respBody = exception.response().errorBody();

            JSONObject errorObject = new JSONObject(respBody.string());
            String error = errorObject.getString("error");
            String errorDescription = errorObject.getString("description");
            CreateUserResponse errResponse = new CreateUserResponse()
                    .setIsError(true)
                    .setError(error)
                    .setErrorDescription(errorDescription);

            Timber.v(errorDescription);
            createUserResponse.onNext(errResponse);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
}
