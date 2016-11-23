package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 10/20/2016.
 */

public class RequestCollectionDao {
    @SerializedName("success")
    private boolean success;
    @SerializedName("request")
    private List<RequestDao> request;
    @SerializedName("message")
    private String message;

    public RequestCollectionDao() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<RequestDao> getRequest() {
        return request;
    }

    public void setRequest(List<RequestDao> request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
