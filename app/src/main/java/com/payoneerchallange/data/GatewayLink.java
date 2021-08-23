package com.payoneerchallange.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * GatewayLink Pojo class
 *
 */
public class GatewayLink {

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("self")
    @Expose
    private String dataSelf;

    @SerializedName("lang")
    @Expose
    private String language;

    @SerializedName("operation")
    @Expose
    private String operation;

    @SerializedName("validation")
    @Expose
    private String validation;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDataSelf() {
        return dataSelf;
    }

    public void setDataSelf(String dataSelf) {
        this.dataSelf = dataSelf;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
