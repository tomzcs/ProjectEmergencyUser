package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 11/21/2016.
 */

public class CarsDao {
    @SerializedName("CAR_ID")
    private int carId;
    @SerializedName("CAR_TYPE")
    private String carType;
    @SerializedName("CAR_NAME")
    private String carName;
    @SerializedName("CAR_COLOR")
    private String carColor;
    @SerializedName("CAR_NUMBER")
    private String carNumber;
    @SerializedName("USER_ID")
    private int userId;

    public CarsDao() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
