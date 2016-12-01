package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarNameDao {
    @SerializedName("CAR_NAME_ID")
    private int carNameId;
    @SerializedName("CAR_NAME_NAME")
    private String carNameName;
    @SerializedName("CAR_TYPE_ID")
    private int carTypeId;

    public CarNameDao(){

    }

    public int getCarNameId() {
        return carNameId;
    }

    public void setCarNameId(int carNameId) {
        this.carNameId = carNameId;
    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarNameName() {
        return carNameName;
    }

    public void setCarNameName(String carNameName) {
        this.carNameName = carNameName;
    }
}
