package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 11/21/2016.
 */

public class CarsCollectionDao {

    @SerializedName("success")
    private boolean success;
    @SerializedName("car")
    private List<CarsDao> car;
    @SerializedName("message")
    private String message;

    public CarsCollectionDao() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<CarsDao> getCar() {
        return car;
    }

    public void setCar(List<CarsDao> car) {
        this.car = car;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
