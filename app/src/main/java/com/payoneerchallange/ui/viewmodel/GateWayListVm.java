package com.payoneerchallange.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.payoneerchallange.data.ApiResponse;
import com.payoneerchallange.data.Gateway;
import com.payoneerchallange.repository.GatewayService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * GateWayListVm -  ViewModel object for communicating with gatewayService.
 * and hold Gateway List data for the view
 */
@HiltViewModel
public class GateWayListVm extends ViewModel {

    //GatewayService injected by Hilt
    GatewayService gatewayService;
    private List<Gateway> gatewayList;

    @Inject
    public GateWayListVm(GatewayService gatewayService){
        this.gatewayService = gatewayService;
       gatewayList = new ArrayList<>();
    }

    public void setGatewayList(List<Gateway> gatewayList) {
        this.gatewayList.clear();
        this.gatewayList.addAll(gatewayList);
    }

    public List<Gateway> getGatewayList() {
        return gatewayList;
    }

    public LiveData<ApiResponse> listGateways(){
        return gatewayService.listPaymentGateway();
    }


//Clean up resources when the viewModel is destroyed
    public void onCleared(){
        gatewayService.cleanup();
    }
}
