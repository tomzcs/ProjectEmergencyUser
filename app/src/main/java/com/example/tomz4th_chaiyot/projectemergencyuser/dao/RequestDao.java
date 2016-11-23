package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 10/20/2016.
 */

public class RequestDao {
    @SerializedName("REQUEST_ID")
    private int requestId;
    @SerializedName("REQUEST_DETAIL")
    private String requestDetail;
    @SerializedName("REQUEST_DETAIL_CAR")
    private String requestDetailCar;
    @SerializedName("REQUEST_LAT")
    private String requestLat;
    @SerializedName("REQUEST_LON")
    private String requestLon;
    @SerializedName("STATUS_ID")
    private int statusId;
    @SerializedName("STATUS_NAME")
    private String statusName;
    @SerializedName("USER_ID")
    private int userId;
    @SerializedName("USER_ID_SERVICE")
    private int userIdService;
    @SerializedName("USER_NAME")
    private String userName;
    @SerializedName("REQUEST_CREATED_AT")
    private String RequestCreatedAt;



    public RequestDao() {

    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    public String getRequestDetailCar() {
        return requestDetailCar;
    }

    public void setRequestDetailCar(String requestDetailCar) {
        this.requestDetailCar = requestDetailCar;
    }

    public String getRequestLat() {
        return requestLat;
    }

    public void setRequestLat(String requestLat) {
        this.requestLat = requestLat;
    }

    public String getRequestLon() {
        return requestLon;
    }

    public void setRequestLon(String requestLon) {
        this.requestLon = requestLon;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserIdService() {
        return userIdService;
    }

    public void setUserIdService(int userIdService) {
        this.userIdService = userIdService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestCreatedAt() {
        return RequestCreatedAt;
    }

    public void setRequestCreatedAt(String requestCreatedAt) {
        RequestCreatedAt = requestCreatedAt;
    }
}
