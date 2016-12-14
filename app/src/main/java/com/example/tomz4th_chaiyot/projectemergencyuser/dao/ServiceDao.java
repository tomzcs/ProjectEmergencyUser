package com.example.tomz4th_chaiyot.projectemergencyuser.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by toMz4th-ChaiYot on 12/4/2016.
 */

public class ServiceDao {
    @SerializedName("SERVICE_ID")
    private int serviceId;
    @SerializedName("SERVICE_NAME")
    private String serviceName;
    @SerializedName("SERVICE_DETAIL")
    private String serviceDetail;
    @SerializedName("SERVICE_ADD")
    private String serviceAdd;
    @SerializedName("SERVICE_TEL")
    private String serviceTel;
    @SerializedName("SERVICE_LAT")
    private String serviceLat;
    @SerializedName("SERVICE_LON")
    private String serviceLon;
    @SerializedName("SERVICE_IMG")
    private String serviceImg;
    @SerializedName("SERVICE_STATUS")
    private int serviceStatus;
    @SerializedName("SERVICE_OPEN")
    private String serviceOpen;
    @SerializedName("SERVICE_CLOSE")
    private String serviceClose;
    @SerializedName("USER_ID_SERVICE")
    private int userServiceId;
    @SerializedName("USER_NAME")
    private String userName;
    @SerializedName("USER_IMG")
    private String userImg;

    public ServiceDao() {

    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getServiceAdd() {
        return serviceAdd;
    }

    public void setServiceAdd(String serviceAdd) {
        this.serviceAdd = serviceAdd;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getServiceLat() {
        return serviceLat;
    }

    public void setServiceLat(String serviceLat) {
        this.serviceLat = serviceLat;
    }

    public String getServiceLon() {
        return serviceLon;
    }

    public void setServiceLon(String serviceLon) {
        this.serviceLon = serviceLon;
    }

    public String getServiceImg() {
        return serviceImg;
    }

    public void setServiceImg(String serviceImg) {
        this.serviceImg = serviceImg;
    }

    public String getServiceOpen() {
        return serviceOpen;
    }

    public void setServiceOpen(String serviceOpen) {
        this.serviceOpen = serviceOpen;
    }

    public String getServiceClose() {
        return serviceClose;
    }

    public void setServiceClose(String serviceClose) {
        this.serviceClose = serviceClose;
    }

    public int getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(int userServiceId) {
        this.userServiceId = userServiceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
