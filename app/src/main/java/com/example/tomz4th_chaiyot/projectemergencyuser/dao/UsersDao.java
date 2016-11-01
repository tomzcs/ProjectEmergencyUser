package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 10/20/2016.
 */

public class UsersDao {
    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String name;

    @SerializedName("user_tel")
    private String tel;

    @SerializedName("user_email")
    private String email;

    @SerializedName("user_password")
    private String password;

    @SerializedName("user_salt")
    private String salt;

    @SerializedName("user_created_at")
    private String createdAt;

    @SerializedName("user_change_at")
    private String changeAt;

    @SerializedName("user_type")
    private String type;

    public UsersDao() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(String changeAt) {
        this.changeAt = changeAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
