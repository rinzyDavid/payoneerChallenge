package com.payoneerchallange.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 *
 * Pojo class : Gateway class object
 */
public class Gateway {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("method")
    @Expose
    private String method;

    @SerializedName("grouping")
    @Expose
    private String grouping;

    @SerializedName("links")
    @Expose
    private GatewayLink link;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public GatewayLink getLink() {
        return link;
    }

    public void setLink(GatewayLink link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gateway gateway = (Gateway) o;
        return code.equals(gateway.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
