package com.payoneerchallange.repository;

import androidx.lifecycle.MutableLiveData;

import com.payoneerchallange.data.ApiResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * GatewayService Implementation class. Depends on PaymentApi Retrofit class for api communication.
 * Dependency injected by Hilt.
 */

public class GatewayServiceImpl implements GatewayService {


   @Inject
    PaymentApi networkApi; // Injected by Hilt for api communication
   private final CompositeDisposable disposable;

   //using constructor injection to inject PaymentApi dependency
   @Inject
   public GatewayServiceImpl(PaymentApi networkApi){
       this.networkApi = networkApi;
       disposable = new CompositeDisposable();
   }

    /**
     * Lists payment gateways available. Thread is managed using RxJava observable
     * @return MutableLiveData
     */
    @Override
    public MutableLiveData<ApiResponse> listPaymentGateway() {


       MutableLiveData<ApiResponse> response = new MutableLiveData<>();

       disposable.add(networkApi.listPaymentGateway()
       .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(response::setValue, throwable -> {
                     String error = new ApiException(throwable).getError();
                     ApiResponse errorResponse = new ApiResponse();
                     errorResponse.setError(error);
                     response.setValue(errorResponse);
       }));
        return response;
    }

@Override
    public void cleanup(){
       if(!disposable.isDisposed())
           disposable.dispose();

    }
}
