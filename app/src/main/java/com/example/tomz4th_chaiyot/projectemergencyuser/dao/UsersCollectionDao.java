package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by toMz4th-ChaiYot on 10/20/2016.
 */

public class UsersCollectionDao {
    @SerializedName("success")
    private boolean success;
    @SerializedName("user")
    private List<UsersDao> user;
    @SerializedName("message")
    private String message;

    public UsersCollectionDao() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UsersDao> getUser() {
        return user;
    }

    public void setUser(List<UsersDao> user) {
        this.user = user;
    }
}
