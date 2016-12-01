package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarTypeCollectionDao {

    @SerializedName("success")
    private boolean success;
    @SerializedName("numRows")
    private int numRows;
    @SerializedName("carType")
    private List<CarTypeDao> carType;
    @SerializedName("message")
    private String message;

    public CarTypeCollectionDao(){

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public List<CarTypeDao> getCarType() {
        return carType;
    }

    public void setCarType(List<CarTypeDao> carType) {
        this.carType = carType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
