package com.payoneerchallange.repository;

import androidx.lifecycle.MutableLiveData;

import com.payoneerchallange.data.ApiResponse;

/**
 * GatewayService- This interface exposes method for making api calls and to clean up resources
 */

public interface GatewayService {

    MutableLiveData<ApiResponse>listPaymentGateway();
    void cleanup();
}
