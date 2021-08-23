package com.payoneerchallange.repository;

import com.payoneerchallange.data.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PaymentApi {

    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Observable<ApiResponse> listPaymentGateway();
}
