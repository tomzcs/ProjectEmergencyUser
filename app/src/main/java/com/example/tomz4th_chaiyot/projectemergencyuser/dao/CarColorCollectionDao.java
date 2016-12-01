package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarColorCollectionDao {
    @SerializedName("success")
    private boolean success;
    @SerializedName("numRows")
    private int numRows;
    @SerializedName("carColor")
    private List<CarColorDao> carColor;
    @SerializedName("message")
    private String message;

    public CarColorCollectionDao(){

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

    public List<CarColorDao> getCarColor() {
        return carColor;
    }

    public void setCarColor(List<CarColorDao> carColor) {
        this.carColor = carColor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
