package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 12/26/2016.
 */

public class RateDao {
    @SerializedName("total") private float total;
    @SerializedName("count") private float count;

    public RateDao(){}

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }
}
