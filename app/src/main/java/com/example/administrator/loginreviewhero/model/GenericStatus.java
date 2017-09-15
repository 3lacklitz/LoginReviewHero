package com.example.administrator.loginreviewhero.model;

import com.google.gson.annotations.SerializedName;

public class GenericStatus {
    @SerializedName("status_code")
    private Integer statusCode;
    @SerializedName("status_description")
    private String statusDescription;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
