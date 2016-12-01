package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarNameCollectionDao {
    @SerializedName("success")
    private boolean success;
    @SerializedName("numRows")
    private int numRows;
    @SerializedName("carName")
    private List<CarNameDao> carName;
    @SerializedName("message")
    private String message;

    public CarNameCollectionDao(){

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

    public List<CarNameDao> getCarName() {
        return carName;
    }

    public void setCarName(List<CarNameDao> carName) {
        this.carName = carName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
