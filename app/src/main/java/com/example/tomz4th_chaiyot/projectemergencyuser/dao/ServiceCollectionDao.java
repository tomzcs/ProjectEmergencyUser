package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 12/4/2016.
 */

public class ServiceCollectionDao {
    @SerializedName("success") private boolean success;
    @SerializedName("service") private List<ServiceDao> service;
    @SerializedName("message") private String message;

    public ServiceCollectionDao(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ServiceDao> getService() {
        return service;
    }

    public void setService(List<ServiceDao> service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
