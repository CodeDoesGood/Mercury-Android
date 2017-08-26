package org.codedoesgood.mercury.api;

import org.codedoesgood.mercury.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Helper class for retrieving a Retrofit instance.
 * <p>
 *     You may also obtain a debug Retrofit instance with an HTTPLoggingInterceptor
 *     attached by calling {@link #getDebugClient()}.
 *     Note: If not a debug build, a standard Retrofit instance without the HTTPLoggingInterceptor
 *     will be returned.
 */
final class RetrofitClient {

    private static Retrofit client = null;
    private static Retrofit debugClient = null;

    private RetrofitClient() { }

    /**
     * Used to return a standard Retrofit instance.
     * @return A standard Retrofit instance with {@link RxJava2CallAdapterFactory}
     * and {@link GsonConverterFactory} preset.
     */
    static Retrofit getClient() {
        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(ApiUtility.getMercuryBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return client;
    }

    /**
     * Used to return a debugging Retrofit instance. If not a debug build, a standard Retrofit
     * instance is returned
     * @return A debugging Retrofit instance with {@link HttpLoggingInterceptor},
     * {@link RxJava2CallAdapterFactory}, and {@link GsonConverterFactory} preset.
     */
    static Retrofit getDebugClient() {
        if (BuildConfig.DEBUG) {
            if (debugClient == null) {
                OkHttpClient.Builder httpClient;
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.v(message));
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                client = new Retrofit.Builder()
                        .baseUrl(ApiUtility.getMercuryBaseUrl())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
            return debugClient;
        } else {
            return getClient();
        }
    }
}
