package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 10/20/2016.
 */

public class UsersDao {
    @SerializedName("USER_ID")
    private int userId;

    @SerializedName("USER_NAME")
    private String name;

    @SerializedName("USER_TEL")
    private String tel;

    @SerializedName("USER_EMAIL")
    private String email;

    @SerializedName("USER_PASSWORD")
    private String password;

    @SerializedName("USER_SALT")
    private String salt;

    @SerializedName("USER_IMG")
    private String img;

    @SerializedName("USER_CREATED_AT")
    private String createdAt;

    @SerializedName("USER_CHANGE_AT")
    private String changeAt;

    @SerializedName("USER_TYPE_ID")
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
