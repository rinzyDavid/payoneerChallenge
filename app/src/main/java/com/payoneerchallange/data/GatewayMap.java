package com.payoneerchallange.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GatewayMap {

    @SerializedName("applicable")
    @Expose
    private List<Gateway> gateways;

    public List<Gateway> getGateways() {
        return gateways;
    }

    public void setGateways(List<Gateway> gateways) {
        this.gateways = gateways;
    }
}
