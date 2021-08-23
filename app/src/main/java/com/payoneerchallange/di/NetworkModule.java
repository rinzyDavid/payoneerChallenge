package com.payoneerchallange.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payoneerchallange.repository.PaymentApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger Hilt module for inject Retrofit Class into GateWayService
 * used to access network
 */
@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {


    static String baseUrl = "https://raw.githubusercontent.com/";


    /**
     * Provides Gson Object
     * @return Gson object
     */
    @Provides
    static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create();
    }

    @Provides
    static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //client.cache(cache);
        return client.build();
    }


    /**
     * Provides PaymentApi retrofit class for communicating with apis
     * @param gson Gson object
     * @param okHttpClient Okhttpclient object
     * @return PaymentApi Retrofit object
     */
    @Provides
    static PaymentApi provideNetworkApi(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        return retrofit.create(PaymentApi.class);
    }

}
