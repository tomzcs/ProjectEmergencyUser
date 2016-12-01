package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 11/30/2016.
 */

public class CarColorDao {
    @SerializedName("CAR_COLOR_ID")
    private int carColorId;
    @SerializedName("CAR_COLOR_NAME")
    private String carColorName;


    public CarColorDao(){

    }

    public int getCarColorId() {
        return carColorId;
    }

    public void setCarColorId(int carColorId) {
        this.carColorId = carColorId;
    }

    public String getCarColorName() {
        return carColorName;
    }

    public void setCarColorName(String carColorName) {
        this.carColorName = carColorName;
    }
}
