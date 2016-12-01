package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarTypeDao {
    @SerializedName("CAR_TYPE_ID")
    private int carTypeId;
    @SerializedName("CAR_TYPE_NAME")
    private String carTypeName;

    public CarTypeDao(){

    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}
