package com.payoneerchallange.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("networks")
    @Expose
    private GatewayMap dataMap;

    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public GatewayMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(GatewayMap dataMap) {
        this.dataMap = dataMap;
    }
}
